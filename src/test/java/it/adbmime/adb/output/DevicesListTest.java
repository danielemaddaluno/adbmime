package it.adbmime.adb.output;

import it.adbmime.adb.devices.DevicesList;
import org.junit.jupiter.api.Test;

public class DevicesListTest {
	private final static String RESULT_1 = "List of devices attached\n" +
			"adb-18171JEC200320-aN96KU._adb-tls-connect._tcp.\tdevice\n" +
			"\n";

	private final static String RESULT_2 = "List of devices attached\n" +
			"adb-18171JEC200320-aN96KU._adb-tls-connect._tcp.\tdevice\n" +
			"emulator-5554\tdevice\n" +
			"\n";

	@Test
	public void testDevicesList() {
		DevicesList screenSize = DevicesList.newInstance();
		System.out.println(screenSize.getDevices());
	}
}
