package it.adbmime.adb;

import org.junit.jupiter.api.Test;

public class DeviceTapTest {
	@Test
	public void testDeviceTap() {
		DeviceScreenSize screenSize = DeviceOutput.getScreenSize();
		DeviceTap deviceTap = DeviceOutput.getTap(screenSize);
		System.out.println(deviceTap);
	}
}
