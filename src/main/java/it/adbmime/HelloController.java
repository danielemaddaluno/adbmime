package it.adbmime;

import it.adbmime.adb.AdbHelper;
import it.adbmime.adb.PhysicalScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageView;

    @FXML
    protected void initialize() {
        PhysicalScreen screen = AdbHelper.getScreen();
        imageView.setImage(screen.getImage());
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}