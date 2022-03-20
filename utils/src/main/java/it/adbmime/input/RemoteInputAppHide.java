package it.adbmime.input;

public final class RemoteInputAppHide extends RemoteInput {
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
    public String toString() {
        return "RemoteInputAppHide{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
