package org.howard.edu.lsp.assignment3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class that orchestrates the entire ETL (Extract, Transform, Load) process.
 * It is responsible for initializing the process, coordinating the different components,
 * and reporting a summary of the run.
 */
public class ETLPipeline {

    /**
     * Main method to execute the ETL process.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        final String INPUT_FILE = "data/products.csv";
        final String OUTPUT_FILE = "data/transformed_products.csv";

        // Initialize components
        CSVReader reader = new CSVReader(INPUT_FILE);
        ProductTransformer transformer = new ProductTransformer();
        CSVWriter writer = new CSVWriter(OUTPUT_FILE);

        List<Product> extractedProducts = new ArrayList<>();
        List<Product> transformedProducts = new ArrayList<>();

        try {
            // 1. Extract
            extractedProducts = reader.readProducts();
            
            // 2. Transform
            for (Product product : extractedProducts) {
                transformedProducts.add(transformer.transform(product));
            }
            
            // 3. Load
            writer.writeProducts(transformedProducts);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file '" + INPUT_FILE + "' not found. Please ensure the file exists in the 'data' directory.");
            return;
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Print the run summary
        System.out.println("ETL Process Complete.");
        System.out.println("----------------------");
        System.out.println("Summary:");
        System.out.println("Rows Read: " + reader.getRowsRead());
        System.out.println("Rows Transformed: " + transformedProducts.size());
        System.out.println("Rows Skipped: " + reader.getRowsSkipped());
        System.out.println("Output File: " + OUTPUT_FILE);
    }
}
