package it.adbmime.adb;


public final class RemoteInputEmpty implements RemoteInput {

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputEmpty(){}

    static RemoteInputEmpty newInstance() {
        return new RemoteInputEmpty();
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.EMPTY;
    }

    @Override
    public String command() {
        return type().getCommand();
    }

    @Override
    public RemoteInput send() {
        // Do nothing, it's just an empty line
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputEmpty{}";
    }
}
