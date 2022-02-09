package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdbHelperTests {
	
	@Test
	public void testConstructor() throws Exception {
		String cmd = AdbHelper.run("adb shell getevent -l | grep ABS_MT_POSITION", 4, 2);
		System.out.println(cmd);
	}

	@Test
	public void testHelper() throws Exception {

	}
	
}
