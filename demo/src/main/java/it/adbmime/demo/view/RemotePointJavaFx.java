package it.adbmime.demo.view;

import it.adbmime.input.RemotePoint;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class RemotePointJavaFx {
    private RemotePointJavaFx(){}
    /**
     * https://stackoverflow.com/a/41416574/3138238
     * @param e
     * @return
     */
    public static RemotePoint newInstance(MouseEvent e) {
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
}
