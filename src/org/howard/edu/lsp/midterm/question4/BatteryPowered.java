package org.howard.edu.lsp.midterm.question4;

/**
 * An interface representing the capabilities of a battery-powered device.
 */
public interface BatteryPowered {
    /**
     * Gets the current battery level as a percentage.
     * @return an integer between 0 and 100, inclusive.
     */
    int getBatteryPercent();

    /**
     * Sets the current battery level.
     * @param percent the new battery percentage.
     * @throws IllegalArgumentException if the percentage is not between 0 and 100.
     */
    void setBatteryPercent(int percent);
}