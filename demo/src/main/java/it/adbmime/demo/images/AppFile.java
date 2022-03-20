package it.adbmime.demo.images;

import java.io.InputStream;

public interface AppFile {
	public String getPath();
	
	public static InputStream getInputStream(AppFile appFile){
		return AppFile.class.getResourceAsStream(appFile.getPath());
	}
}
