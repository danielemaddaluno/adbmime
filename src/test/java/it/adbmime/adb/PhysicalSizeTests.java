package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhysicalSizeTests {
	
	@Test
	public void testConstructor() throws Exception {
		PhysicalSize p = new PhysicalSize("Physical size: 1080x2340");
		Assertions.assertEquals(1080, p.getWidth());
		Assertions.assertEquals(2340, p.getHeight());
	}

	@Test
	public void testHelper() throws Exception {
		PhysicalSize size = AdbHelper.getSize();
		Assertions.assertEquals(1080, size.getWidth());
		Assertions.assertEquals(2340, size.getHeight());
	}
	
}
