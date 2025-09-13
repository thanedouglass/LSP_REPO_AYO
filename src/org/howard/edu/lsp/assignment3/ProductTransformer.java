package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A service class responsible for transforming Product objects according to business rules.
 * This class applies all the "T" (Transform) logic of the ETL process.
 */
public class ProductTransformer {

    private static final BigDecimal ELECTRONICS_DISCOUNT_RATE = new BigDecimal("0.10");
    private static final BigDecimal ELECTRONICS_PREMIUM_THRESHOLD = new BigDecimal("500.00");
    private static final BigDecimal LOW_PRICE_THRESHOLD = new BigDecimal("10.00");
    private static final BigDecimal MEDIUM_PRICE_THRESHOLD = new BigDecimal("100.00");
    private static final BigDecimal HIGH_PRICE_THRESHOLD = new BigDecimal("500.00");

    /**
     * Applies all required transformations to a given Product object.
     * The order of operations is:
     * 1. Uppercase name.
     * 2. Apply a 10% discount to "Electronics" products.
     * 3. Recategorize "Electronics" products to "Premium Electronics" if they exceed a certain price.
     * 4. Set the PriceRange based on the final price.
     *
     * @param product The Product object to be transformed.
     * @return The transformed Product object.
     */
    public Product transform(Product product) {
        // (1) Convert name to uppercase.
        product.setName(product.getName().toUpperCase());

        // Store original category for recategorization check.
        String originalCategory = product.getCategory();

        // (2) Apply a 10% discount to "Electronics" category.
        if ("Electronics".equalsIgnoreCase(originalCategory)) {
            BigDecimal discount = product.getPrice().multiply(ELECTRONICS_DISCOUNT_RATE);
            BigDecimal discountedPrice = product.getPrice().subtract(discount);
            product.setPrice(discountedPrice.setScale(2, RoundingMode.HALF_UP));
        } else {
            // Round other prices as well to ensure consistent two decimal places.
            product.setPrice(product.getPrice().setScale(2, RoundingMode.HALF_UP));
        }

        // (3) Recategorize if post-discount price is over $500.00 and original category was "Electronics".
        if ("Electronics".equalsIgnoreCase(originalCategory) && product.getPrice().compareTo(ELECTRONICS_PREMIUM_THRESHOLD) > 0) {
            product.setCategory("Premium Electronics");
        }

        // (4) Add new PriceRange field based on final price.
        product.setPriceRange(determinePriceRange(product.getPrice()));

        return product;
    }

    /**
     * Determines the price range based on the product's final price.
     * @param finalPrice The final price of the product after all discounts.
     * @return The string representation of the price range.
     */
    private String determinePriceRange(BigDecimal finalPrice) {
        if (finalPrice.compareTo(LOW_PRICE_THRESHOLD) <= 0) {
            return "Low";
        } else if (finalPrice.compareTo(MEDIUM_PRICE_THRESHOLD) <= 0) {
            return "Medium";
        } else if (finalPrice.compareTo(HIGH_PRICE_THRESHOLD) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }
}
