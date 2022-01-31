package me.cubixor.telloapi.api;

public interface DroneStatusListener {

    void onLightStrengthPacketReceive(boolean lightOK);


    void onWifiStrengthPacketReceive(int wifiStrength, int wifiInterference);


    void onStatusPacketReceive(DroneStatus droneStatus);

}
