package it.adbmime;

import it.adbmime.adb.AdbHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdbMimeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdbMimeApplication.class.getResource("adbmime-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Adb Mime");
        stage.setScene(scene);
        // used because if you close the app and the Thread is still running, it'll not close immediately
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();

        System.out.println(AdbHelper.getSize());
        System.out.println(AdbHelper.getSize());
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}