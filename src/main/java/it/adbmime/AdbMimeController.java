package it.adbmime;

import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalSize;
import it.adbmime.adb.Screenshot;
import it.adbmime.adb.PhysicalTouch;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class AdbMimeController {

    private PhysicalSize physicalSize;
    @FXML
    private ImageView imageView;
    @FXML
    private TextArea textArea;


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


}