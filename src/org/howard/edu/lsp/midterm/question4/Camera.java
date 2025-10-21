package org.howard.edu.lsp.midterm.question4;

/**
 * Represents a smart camera that is both networked and battery-powered.
 */
public class Camera extends Device implements Networked, BatteryPowered {
    private int batteryPercent;

    /**
     * Constructs a new Camera device.
     * @param id the unique identifier of the device.
     * @param location the physical location of the device.
     * @param initialBattery the initial battery percentage.
     */
    public Camera(String id, String location, int initialBattery) {
        super(id, location);
        this.setBatteryPercent(initialBattery);
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
    public int getBatteryPercent() {
        return this.batteryPercent;
    }

    @Override
    public void setBatteryPercent(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("battery 0..100");
        }
        this.batteryPercent = percent;
    }

    @Override
    public String getStatus() {
        String connStatus = isConnected() ? "up" : "down";
        return "Camera[id=" + getId() + ", loc=" + getLocation() +
               ", conn=" + connStatus + ", batt=" + this.batteryPercent + "%]";
    }
}

