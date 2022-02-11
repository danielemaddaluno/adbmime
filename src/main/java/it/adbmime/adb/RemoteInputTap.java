package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

public final class RemoteInputTap implements RemoteInput {
    protected static final String INPUT_TAP = "adb shell input tap %d %d";
    private final RemotePoint point;

    private RemoteInputTap(RemotePoint point){
        this.point = point;
    }

    protected static RemoteInputTap newInstance(RemotePoint point) {
        return new RemoteInputTap(point);
    }

    protected static RemoteInputTap newInstance(int x, int y) {
        return RemoteInputTap.newInstance(new RemotePoint(x, y));
    }

    protected static RemoteInputTap newInstance(MouseEvent e) {
        RemotePoint point = RemotePoint.newInstance(e);
        return RemoteInputTap.newInstance(point);
    }

    public RemotePoint getPoint() {
        return point;
    }

    public void send() {
        String command = String.format(INPUT_TAP, point.x(), point.y());
        AdbHelper.run(command);
    }

    @Override
    public String toString() {
        return "RemoteInputTap{" +
                "point=" + point +
                '}';
    }
}
