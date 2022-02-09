package it.adbmime.images;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javafx.scene.image.Image;

public class ImageUtils {

	public static ImageIcon imageIcon(String path) {
		URL imgURL = ImageUtils.class.getResource(path);

		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static Image image(String path) {
		InputStream is = ImageUtils.class.getResourceAsStream(path);
		if (is != null) {
			Image i = new Image(is);
			try {
				is.close();
			} catch (IOException e) {
			}
			return i;
		} else {
			System.err.println("Couldn't find image file.");
			return null;
		}
	}

	public static InputStream imageInputStream(BufferedImage img) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return is;
		} catch (IOException e) {
		}
		return null;
	}

}
