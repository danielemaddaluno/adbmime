package it.adbmime.adb;

import it.adbmime.images.ImageUtils;
import javafx.scene.image.Image;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalScreen {
    private final Image image;

    PhysicalScreen(String adbResponse) {
        this.image = null;//ImageUtils.image(screenFile.getAbsolutePath());
    }

    public PhysicalScreen(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
