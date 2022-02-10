package it.adbmime.adb;


import org.junit.jupiter.api.Test;

public class PhysicalTouchTest {

	@Test
	public void testPhysicalTouch() {
		PhysicalSize size = AdbHelper.getSize();
		PhysicalTouch t = AdbHelper.getTouch(size);
		System.out.println(t);
	}
	
}
