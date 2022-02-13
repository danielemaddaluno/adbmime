package it.adbmime.adb;

import org.junit.jupiter.api.Test;

public class RemoteInputTypeTest {

	@Test
	public void testKeyFromCommand() {
		RemoteInput remoteInput = RemoteInputType.fromCommand("adb shell input keyevent 3");
		System.out.println(remoteInput);
	}

	@Test
	public void testSwipeFromCommand() {
		RemoteInput remoteInput = RemoteInputType.fromCommand("adb shell input swipe 11 22 33 44");
		System.out.println(remoteInput);
	}

	@Test
	public void testTapFromCommand() {
		RemoteInput remoteInput = RemoteInputType.fromCommand("adb shell input tap 11 22");
		System.out.println(remoteInput);
	}

	@Test
	public void testTextFromCommand() {
		RemoteInput remoteInput = RemoteInputType.fromCommand("adb shell input text 'Hello world!'");
		System.out.println(remoteInput);
	}


}
