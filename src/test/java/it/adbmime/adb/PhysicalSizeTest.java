package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhysicalSizeTest {

	@Test
	public void testConstructorWithStrings() throws Exception {
		PhysicalSize p = new PhysicalSize("Physical size: 1080x2340", "                ABS_MT_POSITION_X     : value 0, min 0, max 1079, fuzz 0, flat 0, resolution 0\n"
				+ "                ABS_MT_POSITION_Y     : value 0, min 0, max 2339, fuzz 0, flat 0, resolution 0");
		Assertions.assertEquals(1080, p.getWidth());
		Assertions.assertEquals(2340, p.getHeight());
	}

	@Test
	public void testConstructorWithAdb() throws Exception {
		PhysicalSize size = AdbHelper.getSize();
		Assertions.assertEquals(1080, size.getWidth());
		Assertions.assertEquals(2340, size.getHeight());
	}
	
}
