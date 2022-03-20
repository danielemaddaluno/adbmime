package it.adbmime.adb.devices;

import it.adbmime.adb.AdbHelper;

public final class DeviceDisconnect {
    protected static final String DEVICE_DISCONNECT = "adb disconnect";

    public static void disconnect() {
        AdbHelper.run(DEVICE_DISCONNECT);
    }
}
