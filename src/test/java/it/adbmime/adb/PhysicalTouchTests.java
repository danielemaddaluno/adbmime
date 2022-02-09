package it.adbmime.adb;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhysicalTouchTests {
	
	@Test
	public void testConstructor() throws Exception {
		PhysicalTouch t = new PhysicalTouch("/dev/input/event2: EV_ABS       ABS_MT_POSITION_X    00000280\n" +
				"/dev/input/event2: EV_ABS       ABS_MT_POSITION_Y    00000640");
		System.out.println(t);
	}

	@Test
	public void testHelper() throws Exception {
		PhysicalTouch t = AdbHelper.getTouch();
		System.out.println(t);
	}
	
}
