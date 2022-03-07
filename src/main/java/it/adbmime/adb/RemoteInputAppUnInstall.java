package it.adbmime.adb;

/**
 * https://stackoverflow.com/a/45328796/3138238
 */
public final class RemoteInputAppUnInstall implements RemoteInput {
    private String packageName;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputAppUnInstall(String packageName){
        this.packageName = packageName;
    }

    static RemoteInputAppUnInstall newInstance(String text) {
        return new RemoteInputAppUnInstall(text);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.UNINSTALL;
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
        return "RemoteInputUninstall{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
