package it.adbmime.images;

import javafx.scene.image.Image;

import javax.swing.*;

public enum AppFileIcon implements AppFile {
	LOGO("/application/linux/adbmime.png");

	private final String filePath;

	AppFileIcon(String filePath) {
		this.filePath = filePath;
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
