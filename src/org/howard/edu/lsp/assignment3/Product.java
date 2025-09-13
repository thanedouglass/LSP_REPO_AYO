package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Represents a single product with its attributes.
 * This is a data class used to hold the state of a product
 * throughout the ETL process.
 */
public class Product {
    private int productId;
    private String name;
    private BigDecimal price;
    private String category;
    private String priceRange;

    /**
     * Constructs a Product object.
     * @param productId The unique ID of the product.
     * @param name The name of the product.
     * @param price The price of the product.
     * @param category The category of the product.
     */
    public Product(int productId, String name, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.priceRange = ""; // Initialized to empty string, will be set during transformation.
    }

    /**
     * Gets the product ID.
     * @return The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Gets the product name.
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * @param name The new name for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product price.
     * @return The product price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     * @param price The new price for the product.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the product category.
     * @return The product category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     * @param category The new category for the product.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the price range of the product.
     * @return The price range.
     */
    public String getPriceRange() {
        return priceRange;
    }

    /**
     * Sets the price range of the product.
     * @param priceRange The new price range.
     */
    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Converts the Product object to a CSV-formatted string.
     * @return A string representation of the product suitable for writing to a CSV file.
     */
    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s",
                this.productId,
                this.name,
                this.price.toPlainString(),
                this.category,
                this.priceRange);
    }
}
