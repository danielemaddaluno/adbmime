package it.adbmime.images;

import javax.swing.ImageIcon;
import javafx.scene.image.Image;

public enum AppFileIcon implements AppFile {
	THUMB_UP("1f44d.png"), THUMB_DOWN("1f44e.png");

	private final static String IMAGES_FOLDER = "/images/";
	private final String filePath;

	AppFileIcon(String fileName) {
		this.filePath = IMAGES_FOLDER + fileName;
	}

	@Override
	public String getPath() {
		return filePath;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	public ImageIcon createImageIcon() {
		return ImageUtils.imageIcon(this.getPath());
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	public Image createImage() {
		return ImageUtils.image(this.getPath());
	}
}
