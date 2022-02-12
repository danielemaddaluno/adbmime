package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

public final class RemoteInputSwipe implements RemoteInput {
    protected static final String INPUT_SWIPE = "adb shell input swipe %d %d %d %d";
    private final RemotePoint p0;
    private final RemotePoint p1;

    private RemoteInputSwipe(RemotePoint p0, RemotePoint p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    protected static RemoteInputSwipe newInstance(RemotePoint p0, RemotePoint p1) {
        return new RemoteInputSwipe(p0, p1);
    }

    protected static RemoteInputSwipe newInstance(MouseEvent e0, MouseEvent e1) {
        RemotePoint p0 = RemotePoint.newInstance(e0);
        RemotePoint p1 = RemotePoint.newInstance(e1);
        return RemoteInputSwipe.newInstance(p0, p1);
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
        return String.format(INPUT_SWIPE, p0.x(), p0.y(), p1.x(), p1.y());
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputSwipe{" +
                "p0=" + p0 +
                ", p1=" + p1 +
                '}';
    }
}
