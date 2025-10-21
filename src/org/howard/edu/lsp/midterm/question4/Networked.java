package org.howard.edu.lsp.midterm.question4;

/**
 * An interface representing the capabilities of a network-aware device.
 */
public interface Networked {
    /**
     * Connects the device to the network.
     */
    void connect();

    /**
     * Disconnects the device from the network.
     */
    void disconnect();

    /**
     * Checks if the device is currently connected to the network.
     * @return true if connected, false otherwise.
     */
    boolean isConnected();
}