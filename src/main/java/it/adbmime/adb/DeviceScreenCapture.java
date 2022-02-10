package it.adbmime.adb;

import javafx.scene.image.Image;

public final class DeviceScreenCapture {
    protected static final String SCREENCAPTURE = "adb exec-out screencap -p";
    private Image image;

    private DeviceScreenCapture(Image image){
        this.image = image;
    }

    protected static DeviceScreenCapture newInstance() {
        return new DeviceScreenCapture(AdbHelper.runForImage(SCREENCAPTURE));
    }

    public Image getImage() {
        return image;
    }
}
