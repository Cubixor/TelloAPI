package me.cubixor.telloapi.logs;

import me.cubixor.telloapi.logs.logpackets.*;

public interface LogPacketListener {

    void onOsdDataPacketReceived(OsdDataPacket osdDataPacket);

    void onUsonciPacketReceived(UsonicPakcet usonicPakcet);

    void onBatteryInfoPacketReceived(BatteryInfoPacket batteryInfoPacket);

    void onControllerPacketReceived(ControllerPacket controllerPacket);

    void onMvoFeedbackPacketReceived(MvoFeedbackPacket mvoFeedbackPacket);

    void onAircraftConditionPacketReceived(AircraftConditionPacket aircraftConditionPacket);

    void onMotorControlPacketReceived(MotorControlPacket motorControlPacket);

    void onAirCompensateDataPacketReceived(AirCompensateDataPacket airCompensateDataPacket);

    void onAttiMini0PacketReceived(AttiMini0Packet attiMini0Packet);

    void onNsDataComponentPacketReceived(NsDataComponentPacket nsDataComponentPacket);

    void onSerialApiInputsPacketReceived(SerialApiInputsPacket serialApiInputsPacket);

    void onImuEx0PacketReceived(ImuEx0Packet imuEx0Packet);

    void onImuAtti0PacketReceived(ImuAtti0Packet imuAtti0Packet);

    void onNsDataDebugPacketReceived(NsDataDebugPacket nsDataDebugPacket);

    void onFlyLimitVelDebugPacketReceived(FlyLimitVelDebugPacket flyLimitVelDebugPacket);

    void onCtrlAllocationDebugPacketReceived(CtrlAllocationDebugPacket ctrlAllocationDebugPacket);

    void onCtrlHorizAngVelDebugPacketReceived(CtrlHorizAngVelDebugPacket ctrlHorizAngVelDebugPacket);

    void onCtrlHorizAttiDebugPacketReceived(CtrlHorizAttiDebugPacket ctrlHorizAngVelDebugPacket);

    void onCtrlAccVertDebugPacketReceived(CtrlAccVertDebug ctrlAccVertDebug);

    void onCtrlVertDebugPacketReceived(CtrlVertDebug ctrlVertDebug);

    void onCtrlVelVertDebugPacketReceived(CtrlVelVertDebug ctrlVelVertDebug);

    void onCtrlHorizDebugPacketReceived(CtrlHorizDebugPacket ctrlHorizDebugPacket);
}
