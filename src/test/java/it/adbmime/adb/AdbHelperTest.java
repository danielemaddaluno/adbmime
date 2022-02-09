package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdbHelperTest {

	@Test
	public void testConstructor() throws Exception {
		String cmd = run("adb shell getevent -l | grep ABS_MT_POSITION", 4, 2);
		System.out.println(cmd);
	}


	protected static String run(String command, int timeout, int rows){
		try {
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec(command);
			pr.waitFor(timeout, TimeUnit.SECONDS);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			StringBuffer sb = new StringBuffer();
			int size = 0;
			while ((line=buf.readLine())!=null && size < rows) {
				sb.append(line + "\n");
				size++;
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
