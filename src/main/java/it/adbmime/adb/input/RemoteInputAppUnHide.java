package it.adbmime.adb.input;


import it.adbmime.adb.AdbHelper;

public final class RemoteInputAppUnHide implements RemoteInput {
    private String packageName;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputAppUnHide(String packageName){
        this.packageName = packageName;
    }

    static RemoteInputAppUnHide newInstance(String text) {
        return new RemoteInputAppUnHide(text);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.UNHIDE;
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
        return "RemoteInputAppUnHide{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
