package it.adbmime.adb.input;

import it.adbmime.adb.AdbHelper;

/**
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/view/KeyEvent.java
 */
public final class RemoteInputText implements RemoteInput {
    private String text;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputText(String text){
        this.text = text;
    }

    static RemoteInputText newInstance(String text) {
        return new RemoteInputText(text);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.TEXT;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), text.replace(" ", "%s"));
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputText{" +
                "text='" + text + '\'' +
                '}';
    }
}
