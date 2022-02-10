package it.adbmime;

import it.adbmime.adb.AdbHelper;
import it.adbmime.images.AppFileIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdbMimeApplication extends Application {
    private static final String TITLE = "Adb Mime";

    @Override
    public void start(Stage stage) throws IOException {
        setStageIcon(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(AdbMimeApplication.class.getResource("adbmime-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        // used because if you close the app and the Thread is still running, it'll not close immediately
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        stage.setMaximized(true);

        System.out.println(AdbHelper.getSize());
        System.out.println(AdbHelper.getSize());
    }

    private static void setStageIcon(Stage stage) {
        stage.setIconified(false);
        stage.setTitle(TITLE);
        stage.getIcons().add(AppFileIcon.THUMB_UP.createImage());
        stage.setMaximized(true);
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}