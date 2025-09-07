# ETL Pipeline for Product Data
This Java program implements an ETL (Extract, Transform, Load) pipeline that processes product data from a CSV file, applies various transformations, and outputs the transformed data to a new CSV file.

**Assumptions**
1. Input file (data/products.csv) exists in the specified location relative to the project root
2. Input CSV format follows: ProductID, Name, Price, Category
3. Price values are valid decimal numbers that can be parsed by BigDecimal
4. ProductID values are valid integers
5. The first row of the input file is a header that should be skipped
6. Empty lines in the input file should be skipped
7. Malformed rows should be skipped but processing should continue

**Design Notes**
→ Extraction: Reads data from CSV using BufferedReader, skipping header and empty lines

→ Transformation: Converts product names to uppercase, Applies 10% discount to Electronics category products, Recategorizes high-value Electronics (>$500) as "Premium Electronics", Adds PriceRange field based on price thresholds

→ Loading: Writes transformed data to output CSV with additional PriceRange column

→ Error Handling: Gracefully handles malformed data, continues processing other rows

→ Precision: Uses BigDecimal for precise monetary calculations with HALF_UP rounding

**Price Range Categories**
Low: $0.00 - $10.00
Medium: $10.01 - $100.00
High: $100.01 - $500.00
Premium: $500.01 and above

# Documentation of External Internet Sources
**Link:** https://www.google.com/search?q=https://www.baeldung.com/java-bigdecimal-round-half-up
**Usage:** Borrowed the use of BigDecimal with RoundingMode.HALF_UP to ensure accurate and reliable rounding of floating-point numbers for monetary values, avoiding precision issues inherent with double or float.

**Link:** https://www.google.com/search?q=https://docs.oracle.com/javase/tutorial/essential/io/trywithresources.html
**Usage:** The try-with-resources statement is used to automatically manage the BufferedReader and BufferedWriter streams. This common Java practice ensures that resources are closed properly, preventing potential memory leaks and file corruption

**Link:** https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html
Usage: Used BigDecimal class for precise decimal arithmetic operations, specifically for price calculations and discount applications to avoid floating-point precision errors common with primitive types.

**Link:** https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
Usage: Implemented BufferedReader for efficient reading of the input CSV file, handling large files with optimal performance through buffered input operations.

# Documentation of AI Usage
**Initial Problem:** After successfully compiling the ETLPipeline.java program, I encountered a ClassNotFoundException when trying to run it from the command line.
**AI’s Response Excerpt:** The AI's response explained that the error was a result of running the program from the wrong directory. It instructed me to navigate to the project's root directory and execute the java command using the program's fully qualified name, including its package structure.
**Modification and Usage:** I followed the AI's guidance, navigating to the project root and running the command java org.howard.edu.lsp.assignment2.ETLPipeline. The program then executed successfully, resolving the ClassNotFoundException and demonstrating the importance of running Java commands from the project root directory.
