package it.adbmime;

import it.adbmime.adb.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdbMimeController {

    private PhysicalSize physicalSize;
    private PhysicalTouch physicalTouch;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;


    @FXML
    protected void initialize() {
        this.physicalSize = AdbHelper.getSize();
        textArea.appendText(physicalSize + "\n");
        onScreenUpdateButtonClick();
    }

    @FXML
    private void openAbout() throws IOException {
        AdbMimeApplication.setRoot("about");
    }

    @FXML
    protected void onHomeButtonClick() {
        AdbHelper.pressHomeButton();
    }

    @FXML
    protected void onBackButtonClick() {
        AdbHelper.pressBackButton();
    }

    @FXML
    protected void onOpenBrowserButtonClick() {
        AdbHelper.pressBrowserButton();
    }

    @FXML
    protected void onScreenUpdateButtonClick() {
        Platform.runLater(() -> {
            Screenshot screen = AdbHelper.getScreen();
            imageView.setImage(screen.getImage());
        });
    }

    @FXML
    protected void onCaptureTapButtonClick() {
        Platform.runLater(() -> {
            this.physicalTouch = AdbHelper.getTouch(physicalSize);
            textArea.appendText(physicalTouch + "\n");
        });
    }

    @FXML
    protected void onReplayTapButtonClick() {
        Platform.runLater(() -> {
            if(physicalTouch != null){
               physicalTouch.tap();
            }
        });
    }

    @FXML
    public void textFieldAction(ActionEvent ae){
        AdbHelper.writeText(textField.getText());
    }

    /**
     * https://stackoverflow.com/a/41416574/3138238
     * @param e
     */
    @FXML
    public void onMouseClickedAction(MouseEvent e){
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

        PhysicalTouch physicalTouch = PhysicalTouch.newInstance(xCord, yCord);
        physicalTouch.tap();

        textArea.appendText(physicalTouch + "\n");
    }

}