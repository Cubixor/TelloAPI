package me.cubixor.telloapi.api.listeners;

import me.cubixor.telloapi.api.DroneStatus;
import me.cubixor.telloapi.api.video.SmartVideoMode;

public interface DroneStatusListener {

    /**
     * Called when light strength packet is received from a drone.
     * The packet is sent about every 2 seconds.
     *
     * @param lightOK true if drone sensors receive enough amount of light and false if they don't
     */
    void onLightStrengthPacketReceive(boolean lightOK);

    /**
     * Called when Wi-Fi strength packed is received from a drone.
     *
     * @param wifiStrength     values rounded to 10, from 0 to 90
     * @param wifiInterference TODO Wi-Fi interference
     */
    void onWifiStrengthPacketReceive(int wifiStrength, int wifiInterference);


    /**
     * Called when drone status packet is received from a drone.
     * It contains multiple useful information about possible errors, speed, height, battery and similar things.
     * Not all fields are set.
     *
     * @param droneStatus drone status object
     */
    void onStatusPacketReceive(DroneStatus droneStatus);

    /**
     * Called when smart video state packet is received from a drone.
     * TODO When sent
     *
     * @param smartVideoMode last used video mode
     * @param running        is mentioned video mode running
     */
    void onSmartVideoPacketReceive(SmartVideoMode smartVideoMode, boolean running);

}
