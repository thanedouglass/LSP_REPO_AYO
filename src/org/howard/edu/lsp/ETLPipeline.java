package org.howard.edu.lsp.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal:
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ETLPipeline {

    /**
     * Main method to run the ETL process.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Define input and output file paths relative to the project root.
        final String INPUT_FILE = "data/products.csv";
        final String OUTPUT_FILE = "data/transformed_products.csv";

        // Counters for the run summary.
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        List<String[]> transformedData = new ArrayList<>();

        // 1. Extract and Transform
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            // Read and discard the header row.
            line = reader.readLine();
            if (line != null) {
                // If the file is not empty, read the header.
                // We don't count the header as a data row.
                // This also handles the case of an empty file (only header).
            }

            // Loop through each line of the input file.
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                // Skip empty lines to avoid parsing errors.
                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue;
                }

                String[] columns = line.split(",");

                try {
                    // Extract original data.
                    int productId = Integer.parseInt(columns[0].trim());
                    String name = columns[1].trim();
                    BigDecimal price = new BigDecimal(columns[2].trim());
                    String category = columns[3].trim();

                    // 2. Transform

                    // (1) Convert name to uppercase.
                    name = name.toUpperCase();

                    // (2) Apply a 10% discount to products in the "Electronics" category.
                    if (category.equalsIgnoreCase("Electronics")) {
                        // Discount price by 10%
                        BigDecimal discount = price.multiply(new BigDecimal("0.10"));
                        price = price.subtract(discount);
                    }
                    // Round the final price to two decimal places, rounding half up.
                    price = price.setScale(2, RoundingMode.HALF_UP);

                    // (3) Recategorize "Electronics" if price > $500.00.
                    if (category.equalsIgnoreCase("Electronics") && price.compareTo(new BigDecimal("500.00")) > 0) {
                        category = "Premium Electronics";
                    }

                    // (4) Add new PriceRange field based on final price.
                    String priceRange;
                    if (price.compareTo(new BigDecimal("10.00")) <= 0) {
                        priceRange = "Low";
                    } else if (price.compareTo(new BigDecimal("100.00")) <= 0) {
                        priceRange = "Medium";
                    } else if (price.compareTo(new BigDecimal("500.00")) <= 0) {
                        priceRange = "High";
                    } else {
                        priceRange = "Premium";
                    }
                    
                    // Add the transformed row to the list.
                    transformedData.add(new String[]{
                        String.valueOf(productId),
                        name,
                        price.toPlainString(),
                        category,
                        priceRange
                    });
                    rowsTransformed++;

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Gracefully handle malformed data rows.
                    System.err.println("Skipping malformed row: " + line);
                    rowsSkipped++;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + INPUT_FILE + "' not found. Please ensure the file exists in the 'data' directory.");
            return; // Exit program
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 3. Load
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            // Write the header row.
            writer.write("ProductID,Name,Price,Category,PriceRange");
            writer.newLine();

            // Write the transformed data.
            for (String[] row : transformedData) {
                writer.write(String.join(",", row));
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Print the run summary.
        System.out.println("ETL Process Complete.");
        System.out.println("----------------------");
        System.out.println("Summary:");
        System.out.println("Rows Read: " + rowsRead);
        System.out.println("Rows Transformed: " + rowsTransformed);
        System.out.println("Rows Skipped: " + rowsSkipped);
        System.out.println("Output File: " + OUTPUT_FILE);
    }
}