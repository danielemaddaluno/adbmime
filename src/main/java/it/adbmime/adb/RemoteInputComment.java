package it.adbmime.adb;


public final class RemoteInputComment implements RemoteInput {
    private String comment;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputComment(String comment){
        this.comment = comment;
    }

    static RemoteInputComment newInstance(String comment) {
        return new RemoteInputComment(comment);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.COMMENT;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), comment);
    }

    @Override
    public RemoteInput send() {
        // Do nothing, it's just a comment
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputComment{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
