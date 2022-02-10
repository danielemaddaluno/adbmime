package it.adbmime.adb;

/**
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/view/KeyEvent.java
 */
public class WriteText {
    private static final String INPUT_TEXT = "adb shell input text '%s'";
    private String text;

    protected WriteText(String text){
        this.text = text;
    }

    public static WriteText newInstance(String text) {
        return new WriteText(text);
    }

    public void write() {
        String command = String.format(INPUT_TEXT, text.replace(" ", "%s"));
        AdbHelper.run(command);
    }
}
