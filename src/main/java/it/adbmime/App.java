package it.adbmime;

import it.adbmime.images.AppFileIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static final String TITLE = "Adb Mime";
    private static Scene scene;

    public static Scene getPrimaryScene() {
        return scene;
    }

    @Override
    public void start(Stage stage) throws IOException {
        setStageIcon(stage);
        stage.setMinWidth(1000);
        stage.setMinHeight(800);

        scene = new Scene(loadFXML("adbmime"));
        scene.getStylesheets().add(getClass().getResource("/css/default-theme.css").toExternalForm());

        stage.setScene(scene);
        // used beacause if you close the app and the Thread is still running, it'll not
        // close immediately
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        stage.setMaximized(true);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void setStageIcon(Stage stage) {
        stage.setIconified(false);
        stage.setTitle(TITLE);
        stage.getIcons().add(AppFileIcon.LOGO.createImage());
        stage.setMaximized(true);
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}