package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

public final class RemoteInputTap implements RemoteInput {
    private final RemotePoint point;

    /**
     * This is used to instantiate it with reflection
     */
    private RemoteInputTap(int x0, int y0) {
        this.point = new RemotePoint(x0, y0);
    }

    private RemoteInputTap(RemotePoint point){
        this.point = point;
    }

    protected static RemoteInputTap newInstance(int x, int y) {
        return new RemoteInputTap(x, y);
    }

    protected static RemoteInputTap newInstance(RemotePoint point) {
        return new RemoteInputTap(point);
    }

    protected static RemoteInputTap newInstance(MouseEvent e) {
        RemotePoint point = RemotePoint.newInstance(e);
        return RemoteInputTap.newInstance(point);
    }

    public RemotePoint getPoint() {
        return point;
    }

    @Override
    public RemoteInputType type() {
        return RemoteInputType.TAP;
    }

    @Override
    public String command() {
        return String.format(type().getCommand(), point.x(), point.y());
    }

    @Override
    public RemoteInput send() {
        AdbHelper.run(command());
        return this;
    }

    @Override
    public String toString() {
        return "RemoteInputTap{" +
                "point=" + point +
                '}';
    }
}
