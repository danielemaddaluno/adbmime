package it.adbmime;

import it.adbmime.adb.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class AdbMimeController {
    private DeviceTap deviceTap;
    @FXML
    private ChoiceBox<RemoteInputKey> inputKeyChoiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private StackPane stackPaneForImage;

    @FXML
    protected void initialize() {
        DeviceScreenSize deviceScreenSize = DeviceOutput.getScreenSize();
        textArea.appendText(deviceScreenSize + "\n");

        inputKeyChoiceBox.getItems().addAll(RemoteInputKey.values());
        inputKeyChoiceBox.setValue(RemoteInputKey.HOME);

        onScreenUpdateButtonClick();

//        Image image = AppFileIcon.THUMB_UP.createImage();
//        imageView.setImage(image);
        // https://stackoverflow.com/questions/12630296/resizing-images-to-fit-the-parent-node
        imageView.fitWidthProperty().bind(stackPaneForImage.widthProperty().subtract(10));

    }

    @FXML
    private void openAbout() throws IOException {
        App.setRoot("about");
    }

    @FXML
    protected void onInputKeyButtonClick() {
        inputKeyChoiceBox.getValue().send();
        textArea.appendText(deviceTap + "\n");
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
    protected void onDeleteButtonClick() {
        RemoteInput.delButton().send();
    }

    @FXML
    protected void onEnterButtonClick() {
        RemoteInput.enterButton().send();
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

    @FXML
    public void onMouseClickedAction(MouseEvent e){
        System.out.println(e);
    }

    private MouseEvent lastMousePressed;

    @FXML
    public void onMousePressedAction(MouseEvent e){
        this.lastMousePressed = e;
    }

    @FXML
    public void onMouseReleasedAction(MouseEvent e){
        if(lastMousePressed.getX() == e.getX() && lastMousePressed.getY() == e.getY()){
            RemoteInputTap remoteInputTap = RemoteInput.tap(e);
            remoteInputTap.send();
            textArea.appendText(remoteInputTap + "\n");
        } else {
            RemoteInputSwipe remoteInputSwipe = RemoteInput.swipe(lastMousePressed, e);
            remoteInputSwipe.send();
            textArea.appendText(remoteInputSwipe + "\n");
        }
    }
}