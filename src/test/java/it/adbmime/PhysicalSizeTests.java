package it.adbmime;


import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhysicalSizeTests {
	
	@Test
	public void test_1() throws Exception {
		PhysicalSize p = new PhysicalSize("Physical size: 1080x2340");
		Assertions.assertEquals(1080, p.getWidth());
		Assertions.assertEquals(2340, p.getHeight());
	}
	
}
