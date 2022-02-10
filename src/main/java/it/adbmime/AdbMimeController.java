package it.adbmime;

import it.adbmime.adb.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdbMimeController {
    private DeviceTap deviceTap;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;

    @FXML
    protected void initialize() {
        DeviceScreenSize deviceScreenSize = DeviceOutput.getScreenSize();
        textArea.appendText(deviceScreenSize + "\n");
        onScreenUpdateButtonClick();
    }

    @FXML
    private void openAbout() throws IOException {
        App.setRoot("about");
    }

    @FXML
    protected void onHomeButtonClick() {
        RemoteInput.homeButton().send();
    }

    @FXML
    protected void onBackButtonClick() {
        RemoteInput.backButton().send();
    }

    @FXML
    protected void onOpenBrowserButtonClick() {
        RemoteInput.browserButton().send();
    }

    @FXML
    protected void onScreenUpdateButtonClick() {
        Platform.runLater(() -> {
            DeviceScreenCapture screen = DeviceOutput.getScreenCapture();
            imageView.setImage(screen.getImage());
        });
    }

    @FXML
    protected void onCaptureTapButtonClick() {
        Platform.runLater(() -> {
            this.deviceTap = DeviceOutput.getTap();
            textArea.appendText(deviceTap + "\n");
        });
    }

    @FXML
    protected void onReplayTapButtonClick() {
        Platform.runLater(() -> {
            if(deviceTap != null){
                int x = deviceTap.getX();
                int y = deviceTap.getY();
                RemoteInput.tap(x, y).send();
            }
        });
    }

    @FXML
    public void textFieldAction(ActionEvent ae){
        RemoteInput.text(textField.getText()).send();
    }

    /**
     * https://stackoverflow.com/a/41416574/3138238
     * @param e
     */
    @FXML
    public void onMouseClickedAction(MouseEvent e){
        RemoteInputTap remoteInputTap = RemoteInput.tap(e);
        remoteInputTap.send();
        textArea.appendText(remoteInputTap + "\n");
    }
}