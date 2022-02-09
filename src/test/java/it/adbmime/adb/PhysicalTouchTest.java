package it.adbmime.adb;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhysicalTouchTest {

	@Test
	public void testPhysicalTouch() throws Exception {
		PhysicalTouch t = AdbHelper.getAdbStreamResult(PhysicalTouch.class);
		System.out.println(t);
	}
	
}
