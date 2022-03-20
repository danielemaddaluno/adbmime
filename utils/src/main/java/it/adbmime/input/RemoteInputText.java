package it.adbmime.input;

/**
 * https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/view/KeyEvent.java
 */
public final class RemoteInputText extends RemoteInput {
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
    public String toString() {
        return "RemoteInputText{" +
                "text='" + text + '\'' +
                '}';
    }
}
