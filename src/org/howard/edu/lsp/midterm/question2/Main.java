package org.howard.edu.lsp.midterm.question2;

/**
 * Main class to demonstrate the functionality of the AreaCalculator.
 */
public class Main {

    /**
     * Main entry point of the application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Conceptual Question: Why is overloading better here?
        // Method overloading is the better design choice here because it provides a clean, intuitive API.
        // The single method name "area" represents the same conceptual operation (calculating area)
        // for different shapes, simplifying its usage for the developer. Using different method names
        // like "circleArea" or "rectangleArea" would be more verbose and less elegant.

        // Invoking each overloaded method with valid inputs
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        System.out.println("\n--- Testing Exception Handling ---");

        // Invoking an area method with invalid input to demonstrate exception handling
        try {
            System.out.println("Attempting to calculate area with a negative radius...");
            AreaCalculator.area(-5.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: Dimensions cannot be negative or zero.");
            // System.err.println(e.getMessage()); // Optionally print the specific message from the exception
        }
    }
}