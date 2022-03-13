package it.adbmime.adb.input;


import it.adbmime.adb.AdbHelper;

public final class RemoteInputAppHide implements RemoteInput {
    private String packageName;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputAppHide(String packageName){
        this.packageName = packageName;
    }

    static RemoteInputAppHide newInstance(String text) {
        return new RemoteInputAppHide(text);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.HIDE;
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
        return "RemoteInputAppHide{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
