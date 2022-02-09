module it.adbmime {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.adbmime to javafx.fxml;
    exports it.adbmime;
    exports it.adbmime.adb;
    opens it.adbmime.adb to javafx.fxml;
}