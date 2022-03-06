package it.adbmime.adb;


public class RemoteInputKey implements RemoteInput {
    static final String LONGPRESS = "--longpress ";
    /** Adds a '--longpress' to the command **/
    private boolean longpress;
    private final RemoteInputKeycode keycode;

    RemoteInputKey(boolean longpress, int keycode) {
        this.longpress = longpress;
        this.keycode = RemoteInputKeycode.fromKeycode(keycode);
    }

    protected static RemoteInputKey newInstance(boolean longpress, int keycode) {
        return new RemoteInputKey(longpress, keycode);
    }

    public boolean isLongpress() {
        return longpress;
    }

    public RemoteInputKey longpress(boolean longpress) {
        this.longpress = longpress;
        return this;
    }

    public RemoteInputKey longpress() {
        return longpress(true);
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
        command = isLongpress() ? command : command.replace(LONGPRESS, "");
        return command;
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }
}
