module it.adbmime {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires io.reactivex.rxjava3;
    requires rxjavafx;


    opens it.adbmime to javafx.fxml;
    exports it.adbmime;
    exports it.adbmime.adb;
    opens it.adbmime.adb to javafx.fxml;
    exports it.adbmime.view;
    opens it.adbmime.view to javafx.fxml;
}