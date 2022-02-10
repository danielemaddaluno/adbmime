package it.adbmime.adb;


import org.junit.jupiter.api.Test;

public class PhysicalTouchTest {

	@Test
	public void testPhysicalTouch() {
		PhysicalTouch t = AdbHelper.getTouch();
		System.out.println(t);
	}
	
}
