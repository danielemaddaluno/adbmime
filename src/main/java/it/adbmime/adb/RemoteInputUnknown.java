package it.adbmime.adb;


public final class RemoteInputUnknown implements RemoteInput {
    private String command;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputUnknown(String command){
        this.command = command;
    }

    static RemoteInputUnknown newInstance(String command) {
        return new RemoteInputUnknown(command);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.UNKNOWN;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), command);
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputUnknown{" +
                "command='" + command + '\'' +
                '}';
    }
}
