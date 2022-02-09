package it.adbmime.adb;


import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdbHelperTests {

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

	@Test
	public void testHelper() throws Exception {
		JadbConnection jadb = new JadbConnection();
		List<JadbDevice> devices = jadb.getDevices();
		JadbDevice d = devices.get(0);
		System.out.println(devices.size());

		InputStream is = d.execute("adb shell getevent -l | grep ABS_MT_POSITION");
		InputStreamReader isr = new InputStreamReader(is);

		int data = isr.read();
		while(data != -1){
			char theChar = (char) data;
			data = isr.read();
		}

		isr.close();
	}
	
}
