# ETL Pipeline for Product Data
This Java program implements an ETL (Extract, Transform, Load) pipeline that processes product data from a CSV file, applies various transformations, and outputs the transformed data to a new CSV file.

**How To Run**
1. Compile the Java program:
```java
javac src/org/howard/edu/lsp/assignment2/ETLPipeline.java
```
2. Run the compiled program:
```java
java -cp src org.howard.edu.lsp.assignment2.ETLPipeline
```

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
**Initial Problem:**  After successfully compiling the ETLPipeline.java program, I encountered a ClassNotFoundException when trying to run it from the command line. My initial attempts with different command flags (-d ., -d bin) resulted in the program creating a whole new org/ folder at the project root, which was not the intended behavior.

**My Prompt:**  i need the compiler to place the compiled output in the assignment2 folder src/org/howard/edu/lsp/assignment2/

**AI’s Response Excerpt:** The AI's response explained that to get the compiled .class file to be in the same directory as the .java file, I needed to change my current working directory to the assignment2 folder and run a simple javac command without any destination flags.

**Modification and Usage:** I learned to manage my directory context before compiling. By running cd src/org/howard/edu/lsp/assignment2/ and then javac ETLPipeline.java, the .class file was correctly placed within the assignment2 directory. I then returned to the project root and used the java -cp src ... command, which correctly told the JVM to find the compiled code within the src directory. This process resolved the original error and taught me a crucial lesson about the relationship between javac, java, and the project's file structure.
