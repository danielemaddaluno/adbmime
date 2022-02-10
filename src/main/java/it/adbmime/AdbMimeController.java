package it.adbmime;

import it.adbmime.adb.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AdbMimeController {

    private PhysicalSize physicalSize;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;


    @FXML
    protected void initialize() {
        this.physicalSize = AdbHelper.getSize();
        onScreenUpdateButtonClick();
    }

    @FXML
    protected void onHomeButtonClick() {
        AdbHelper.pressHomeButton();
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
            PhysicalTouch pt = AdbHelper.getTouch();
            textArea.appendText(pt.toString(physicalSize) + "\n");
        });
    }

    @FXML
    public void textFieldAction(ActionEvent ae){
        AdbHelper.writeText(textField.getText());
    }

}