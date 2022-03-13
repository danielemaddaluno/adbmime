package it.adbmime.adb.output;

import it.adbmime.adb.input.RemoteInput;
import it.adbmime.adb.output.DeviceOutput;
import it.adbmime.adb.output.DeviceScreenSize;
import it.adbmime.adb.output.DeviceTap;
import it.adbmime.adb.output.DevicesList;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
