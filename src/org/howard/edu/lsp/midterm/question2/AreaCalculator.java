package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator {

    /**
     * Calculates the area of a circle.
     *
     * @param radius the radius of the circle
     * @return the area of the circle as a double
     * @throws IllegalArgumentException if the radius is not positive
     */
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be a positive number.");
        }
        return Math.PI * radius * radius;
    }

    /**
     * Calculates the area of a rectangle.
     *
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @return the area of the rectangle as a double
     * @throws IllegalArgumentException if width or height is not positive
     */
    public static double area(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive numbers.");
        }
        return width * height;
    }

    /**
     * Calculates the area of a triangle.
     *
     * @param base   the base of the triangle
     * @param height the height of the triangle
     * @return the area of the triangle as a double
     * @throws IllegalArgumentException if base or height is not positive
     */
    public static double area(int base, int height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be positive numbers.");
        }
        return 0.5 * base * height;
    }

    /**
     * Calculates the area of a square.
     *
     * @param side the side length of the square
     * @return the area of the square as a double
     * @throws IllegalArgumentException if the side length is not positive
     */
    public static double area(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be a positive number.");
        }
        return (double) side * side;
    }
}