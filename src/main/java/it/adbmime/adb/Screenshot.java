package it.adbmime.adb;

import javafx.scene.image.Image;

public record Screenshot(Image image) {

    protected static final String SCREENCAPTURE = "adb exec-out screencap -p";

    public static Screenshot newInstance() {
        return new Screenshot(AdbHelper.runForImage(SCREENCAPTURE));
    }

    public Image getImage() {
        return image;
    }

}
