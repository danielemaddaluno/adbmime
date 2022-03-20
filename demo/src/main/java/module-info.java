module it.adbmime.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires it.adbmime;

    opens it.adbmime.demo to javafx.fxml;
    exports it.adbmime.demo;
}