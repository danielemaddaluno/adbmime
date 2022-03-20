package it.adbmime.devices;

import it.adbmime.AdbHelper;

public final class DeviceDisconnect {
    protected static final String DEVICE_DISCONNECT = "adb disconnect";

    public static void disconnect() {
        AdbHelper.run(DEVICE_DISCONNECT);
    }
}
