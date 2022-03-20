package it.adbmime.input;

public final class RemoteInputEmpty extends RemoteInput {

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
        // Do nothing, it's just a comment
        return this;
    }

    @Override
    public RemoteInput send(String deviceId) {
        // Do nothing, it's just a comment
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputEmpty{}";
    }
}
