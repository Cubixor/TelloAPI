package me.cubixor.telloapi.api;

public interface DroneConnectionListener {

    /**
     * Called after the drone was connected with the api
     */
    void onConnect();

    /**
     * Called after the drone was disconnected from the api
     */
    void onDisconnect();
}
