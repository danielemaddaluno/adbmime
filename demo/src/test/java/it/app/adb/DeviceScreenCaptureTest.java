package it.app.adb;

import it.adbmime.output.DeviceOutput;
import it.adbmime.output.DeviceScreenCapture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeviceScreenCaptureTest {
	@Test
	public void testScreenCapture() {
		DeviceScreenCapture screenCapture = DeviceOutput.getScreenCapture();
		Assertions.assertNotNull(screenCapture.getImage());
	}
}
