package it.adbmime.adb;

/**
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/view/KeyEvent.java
 */
public final class RemoteInputText implements RemoteInput {
    private static final String INPUT_TEXT = "adb shell input text '%s'";
    private String text;

    private RemoteInputText(String text){
        this.text = text;
    }

    static RemoteInputText newInstance(String text) {
        return new RemoteInputText(text);
    }

    public void send() {
        String command = String.format(INPUT_TEXT, text.replace(" ", "%s"));
        AdbHelper.run(command);
    }
}
