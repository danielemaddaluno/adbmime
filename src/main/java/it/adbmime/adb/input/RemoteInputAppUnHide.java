package it.adbmime.adb.input;

public final class RemoteInputAppUnHide extends RemoteInput {
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
    public String toString() {
        return "RemoteInputAppUnHide{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
