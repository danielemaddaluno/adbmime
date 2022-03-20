package it.adbmime.output;

import it.adbmime.AdbHelper;

/**
 * adb shell screenrecord --output-format=h264 -
 * https://examples.javacodegeeks.com/desktop-java/javafx/javafx-media-api/
 * https://stackify.dev/649747-use-adb-screenrecord-command-to-mirror-android-screen-to-pc-via-usb
 * https://android.stackexchange.com/questions/7686/is-there-a-way-to-see-the-devices-screen-live-on-pc-through-adb
 * https://stackoverflow.com/questions/39569208/stream-android-screen-to-video-player/39578061#39578061
 * https://stackoverflow.com/questions/13539814/playing-mp3-files-in-javafx-from-input-stream
 * https://opencv-java-tutorials.readthedocs.io/en/latest/03-first-javafx-application-with-opencv.html
 * https://github.com/opencv-java/getting-started
 *
 * adb exec-out screencap -p > screen.png
 * https://stackoverflow.com/questions/27766712/using-adb-to-capture-the-screen
 *
 */
public final class DeviceScreenCapture {
    protected static final String SCREENCAPTURE = "adb exec-out screencap -p";
    private byte[] image;

    private DeviceScreenCapture(byte[] image){
        this.image = image;
    }

    protected static DeviceScreenCapture newInstance() {
        return new DeviceScreenCapture(AdbHelper.runForImage(SCREENCAPTURE));
    }

    public static DeviceScreenCapture newInstance(String deviceId) {
        return new DeviceScreenCapture(AdbHelper.runForImage(SCREENCAPTURE.replace("adb ", "adb -s " + deviceId + " ")));
    }

    public byte[] getImage() {
        return image;
    }
}
