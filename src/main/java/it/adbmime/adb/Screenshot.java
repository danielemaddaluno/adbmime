package it.adbmime.adb;

import javafx.scene.image.Image;

public class Screenshot {
    protected static final String SCREENCAPTURE = "adb exec-out screencap -p";
    private Image image;

    protected Screenshot(Image image){
        this.image = image;
    }

    public static Screenshot newInstance() {
        return new Screenshot(AdbHelper.runForImage(SCREENCAPTURE));
    }

    public Image getImage() {
        return image;
    }
}
