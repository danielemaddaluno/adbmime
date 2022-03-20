package it.adbmime.input;

public class RemoteInputKey extends RemoteInput {
    static final String LONG_PRESS = "--longpress ";
    /** Adds a '--longpress' to the command **/
    private boolean longPress;
    private final RemoteInputKeycode keycode;

    RemoteInputKey(boolean longPress, int keycode) {
        this.longPress = longPress;
        this.keycode = RemoteInputKeycode.fromKeycode(keycode);
    }

    protected static RemoteInputKey newInstance(boolean longpress, int keycode) {
        return new RemoteInputKey(longpress, keycode);
    }

    public boolean isLongPress() {
        return longPress;
    }

    public RemoteInputKey longPress(boolean longPress) {
        this.longPress = longPress;
        return this;
    }

    public RemoteInputKey longPress() {
        return longPress(true);
    }

    public RemoteInputKeycode getKeycode() {
        return keycode;
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.KEY;
    }

    @Override
    public String command() {
        String command = String.format(type().getCommand(), this.getKeycode().getKeycode());
        command = isLongPress() ? command : command.replace(LONG_PRESS, "");
        return command;
    }

}
