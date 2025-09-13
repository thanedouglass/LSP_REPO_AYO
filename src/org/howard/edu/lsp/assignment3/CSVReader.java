package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A service class for the "Extract" part of the ETL process.
 * It is responsible for reading and parsing data from a CSV file.
 */
public class CSVReader {

    private String filePath;
    private int rowsRead = 0;
    private int rowsSkipped = 0;

    /**
     * Constructs a CSVReader object with the specified file path.
     * @param filePath The path to the CSV file.
     */
    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads and parses product data from the CSV file.
     * @return A list of Product objects.
     * @throws FileNotFoundException if the input file does not exist.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public List<Product> readProducts() throws FileNotFoundException, IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read and discard the header row.
            String headerLine = reader.readLine();
            if (headerLine == null) {
                // Handle empty file case
                return products;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                // Skip empty lines to avoid parsing errors.
                if (line.trim().isEmpty()) {
                    rowsSkipped++;
                    continue;
                }

                try {
                    String[] columns = line.split(",");
                    int productId = Integer.parseInt(columns[0].trim());
                    String name = columns[1].trim();
                    BigDecimal price = new BigDecimal(columns[2].trim());
                    String category = columns[3].trim();

                    products.add(new Product(productId, name, price, category));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping malformed row: " + line);
                    rowsSkipped++;
                }
            }
        }
        return products;
    }

    /**
     * Gets the number of data rows read from the file.
     * @return The total number of rows (excluding header) read.
     */
    public int getRowsRead() {
        return rowsRead;
    }

    /**
     * Gets the number of malformed or empty rows skipped during reading.
     * @return The total number of rows skipped.
     */
    public int getRowsSkipped() {
        return rowsSkipped;
    }
}