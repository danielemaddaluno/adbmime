package it.adbmime.adb;


public final class RemoteInputSleep implements RemoteInput {
    private int seconds;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputSleep(int seconds){
        this.seconds = seconds;
    }

    static RemoteInputSleep newInstance(int seconds) {
        return new RemoteInputSleep(seconds);
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.SLEEP;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), seconds);
    }

    @Override
    public RemoteInput send() {
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
        }
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputSleep{" +
                "seconds='" + seconds + '\'' +
                '}';
    }
}
