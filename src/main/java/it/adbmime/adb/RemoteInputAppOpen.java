package it.adbmime.adb;

/**
 * https://stackoverflow.com/a/46935109/3138238
 */
public final class RemoteInputAppOpen implements RemoteInput {
    private String packageName;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputAppOpen(String packageName){
        this.packageName = packageName;
    }

    static RemoteInputAppOpen newInstance(String text) {
        return new RemoteInputAppOpen(text);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.OPEN;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), packageName);
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputOpen{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}