package it.adbmime.adb;

import org.junit.jupiter.api.Test;

public class AdbConnectDisconnectTest {

	@Test
	public void disconnectTest() {
		String result = AdbHelper.run("adb disconnect");
		System.out.println(result);
	}

	@Test
	public void connectTest() {
		String result = AdbHelper.run("adb connect 192.168.1.86:42593");
		System.out.println(result);
	}



}
