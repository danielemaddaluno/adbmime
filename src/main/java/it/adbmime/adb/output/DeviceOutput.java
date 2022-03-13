package it.adbmime.adb.output;

public interface DeviceOutput {
    static DeviceScreenSize getScreenSize(){
        return DeviceScreenSize.newInstance();
    }

    static DeviceScreenCapture getScreenCapture(){
        return DeviceScreenCapture.newInstance();
    }

    static DeviceTap getTap(){
        DeviceScreenSize deviceScreenSize = getScreenSize();
        return DeviceTap.newInstance(deviceScreenSize);
    }

    static DeviceTap getTap(DeviceScreenSize deviceScreenSize){
        return DeviceTap.newInstance(deviceScreenSize);
    }
}