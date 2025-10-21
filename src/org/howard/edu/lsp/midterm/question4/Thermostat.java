package org.howard.edu.lsp.midterm.question4;

/**
 * Represents a smart thermostat that is networked.
 */
public class Thermostat extends Device implements Networked {
    private double temperatureC;

    /**
     * Constructs a new Thermostat device.
     * @param id the unique identifier of the device.
     * @param location the physical location of the device.
     * @param initialTempC the initial temperature in Celsius.
     */
    public Thermostat(String id, String location, double initialTempC) {
        super(id, location);
        this.temperatureC = initialTempC;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    @Override
    public void connect() {
        setConnected(true);
    }

    @Override
    public void disconnect() {
        setConnected(false);
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public String getStatus() {
        String connStatus = isConnected() ? "up" : "down";
        return "Thermostat[id=" + getId() + ", loc=" + getLocation() +
               ", conn=" + connStatus + ", tempC=" + this.temperatureC + "]";
    }
}