package it.adbmime.output;

public interface DeviceOutput {
    static DeviceScreenSize getScreenSize(){
        return DeviceScreenSize.newInstance();
    }

    static DeviceScreenCapture getScreenCapture(){
        return DeviceScreenCapture.newInstance();
    }

    static DeviceScreenCapture getScreenCapture(String deviceId){
        return DeviceScreenCapture.newInstance(deviceId);
    }

    static DeviceTap getTap(){
        DeviceScreenSize deviceScreenSize = getScreenSize();
        return DeviceTap.newInstance(deviceScreenSize);
    }

    static DeviceTap getTap(DeviceScreenSize deviceScreenSize){
        return DeviceTap.newInstance(deviceScreenSize);
    }
}
