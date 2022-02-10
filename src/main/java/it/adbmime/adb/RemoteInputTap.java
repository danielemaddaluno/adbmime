package it.adbmime.adb;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public final class RemoteInputTap implements RemoteInput {
    protected static final String INPUT_TAP = "adb shell input tap %d %d";
    private final int x;
    private final int y;

    private RemoteInputTap(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected static RemoteInputTap newInstance(int x, int y) {
        return new RemoteInputTap(x, y);
    }

    protected static RemoteInputTap newInstance(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        ImageView view = (ImageView) e.getSource();
        Bounds bounds = view.getLayoutBounds();
        double xScale = bounds.getWidth() / view.getImage().getWidth();
        double yScale = bounds.getHeight() / view.getImage().getHeight();

        x /= xScale;
        y /= yScale;

        int xCord = (int) x;
        int yCord = (int) y;

        return RemoteInputTap.newInstance(xCord, yCord);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void send() {
        String command = String.format(INPUT_TAP, getX(), getY());
        AdbHelper.run(command);
    }

    @Override
    public String toString() {
        return "PhysicalTouch{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
