package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A service class for the "Load" part of the ETL process.
 * It is responsible for writing a list of Product objects to a CSV file.
 */
public class CSVWriter {

    private String filePath;

    /**
     * Constructs a CSVWriter object with the specified file path.
     * @param filePath The path to the output CSV file.
     */
    public CSVWriter(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes a list of Product objects to a CSV file.
     * The output file will include a header row and the transformed data.
     * @param products The list of Product objects to write.
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public void writeProducts(List<Product> products) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header row.
            writer.write("ProductID,Name,Price,Category,PriceRange");
            writer.newLine();

            // Write the transformed data.
            for (Product product : products) {
                writer.write(product.toString());
                writer.newLine();
            }
        }
    }
}