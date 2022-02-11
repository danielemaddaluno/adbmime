package it.adbmime.adb;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DeviceTapTest {
	@Test
	public void testDeviceTap() {
		DeviceScreenSize screenSize = DeviceOutput.getScreenSize();
		DeviceTap deviceTap = DeviceOutput.getTap(screenSize);
		System.out.println(deviceTap);
	}

	@Test
	public void getMaxHexValue() throws InterruptedException {
		final Semaphore s = new Semaphore(0);
		final DeviceScreenSize screenSize = DeviceOutput.getScreenSize();

		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				RemoteInput.tap(screenSize.getWidth() -1, screenSize.getHeight()-1).send();
			}
		}, 2, TimeUnit.SECONDS);

		executor.schedule(new Runnable() {
			@Override
			public void run() {
				s.release();
			}
		}, 5, TimeUnit.SECONDS);

		DeviceTap deviceTap = DeviceOutput.getTap(screenSize);
		System.out.println(deviceTap);
		s.acquire();
	}
}
