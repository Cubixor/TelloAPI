package me.cubixor.telloapi.api.listeners;

public interface DroneConnectionListener {

    /**
     * Called when the drone connects to the api.
     */
    void onConnect();

    /**
     * Called when the drone disconnects from the api.
     */
    void onDisconnect();
}
