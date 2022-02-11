package it.adbmime.adb;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public record RemotePoint(int x, int y) {
    /**
     * https://stackoverflow.com/a/41416574/3138238
     * @param e
     * @return
     */
    protected static RemotePoint newInstance(MouseEvent e) {
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

        return new RemotePoint(xCord, yCord);
    }

    @Override
    public String toString() {
        return "RemotePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
