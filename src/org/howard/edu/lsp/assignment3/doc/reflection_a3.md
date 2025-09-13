# Assignment 3 Reflection 
This new design adheres to a number of object-oriented principles, most notably the Single Responsibility Principle, where each class is designed to handle one specific piece of the ETL process. This makes the code much more modular and easier to understand, debug, and test. The core of the redesign is breaking the problem into distinct classes (Product, CSVReader, ProductTransformer, CSVWriter, ETLPipeline), each with a single, clear responsibility. The new framework maintains the exact same logic and behavior as my original code from assignment 2, ensuring the same inputs, outputs, transformations, and error handling. The transformation logic (rounding, recategorization, price range) and error handling for missing/empty files remain unchanged.

**The new design includes:**

Product: A data class to represent a single product record.

ProductTransformer: A class responsible for applying all the business logic to a Product object.

CSVReader: A class for the "Extract" part of the process, handling file reading and parsing.

CSVWriter: A class for the "Load" part, handling writing the data back to a file.

ETLPipeline: The main class that orchestrates the entire process, tying the other classes together.

This refactoring adheres to the Single Responsibility Principle, making it much easier to test and modify each part independently.

#How the New Design Meets the Requirements

#####Object-Oriented Decomposition:
The core of the redesign is breaking the problem into distinct classes (Product, CSVReader, ProductTransformer, CSVWriter, ETLPipeline), each with a single, clear responsibility. This directly addresses the main goal of the assignment.

#####Correctness:
The new framework maintains the exact same logic and behavior as your original code, ensuring the same inputs, outputs, transformations, and error handling. I've verified that the transformation logic (rounding, recategorization, price range) and error handling for missing/empty files remain unchanged.

#####Encapsulation: 
The Product class encapsulates the data (its attributes are private) and provides public methods to interact with that data (getters and setters). This is a foundational OO concept that was missing in the original implementation.

#####Code Style & Documentation: 
Each class and public method now includes Javadoc comments, which are essential for good documentation. The code is also organized into a package, org.howard.edu.lsp.assignment3, as required.

