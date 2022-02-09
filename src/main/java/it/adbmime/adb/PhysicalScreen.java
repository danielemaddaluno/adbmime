package it.adbmime.adb;

import it.adbmime.images.ImageUtils;
import javafx.scene.image.Image;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhysicalScreen {
    private final Image image;

    PhysicalScreen(File screenFile) {
        this.image = ImageUtils.image(screenFile.getAbsolutePath());
    }

    public Image getImage() {
        return image;
    }
}
