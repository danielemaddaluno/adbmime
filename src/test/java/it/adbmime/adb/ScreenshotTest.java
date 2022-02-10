package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenshotTest {

	@Test
	public void testScreenshot() {
		Screenshot screenshot = AdbHelper.getScreen();
		Assertions.assertNotNull(screenshot.getImage());
	}
	
}
