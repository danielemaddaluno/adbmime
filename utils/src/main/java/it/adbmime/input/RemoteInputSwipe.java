package it.adbmime.input;

public final class RemoteInputSwipe extends RemoteInput {
    private final RemotePoint p0;
    private final RemotePoint p1;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputSwipe(int x0, int y0, int x1, int y1) {
        this.p0 = new RemotePoint(x0, y0);
        this.p1 = new RemotePoint(x1, y1);
    }

    private RemoteInputSwipe(RemotePoint p0, RemotePoint p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    protected static RemoteInputSwipe newInstance(int x0, int y0, int x1, int y1) {
        return new RemoteInputSwipe(x0, y0, x1, y1);
    }

    protected static RemoteInputSwipe newInstance(RemotePoint p0, RemotePoint p1) {
        return new RemoteInputSwipe(p0, p1);
    }

    public RemotePoint getP0() {
        return p0;
    }

    public RemotePoint getP1() {
        return p1;
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.SWIPE;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), p0.x(), p0.y(), p1.x(), p1.y());
    }

    @Override
    public String toString() {
        return "RemoteInputSwipe{" +
                "p0=" + p0 +
                ", p1=" + p1 +
                '}';
    }
}
