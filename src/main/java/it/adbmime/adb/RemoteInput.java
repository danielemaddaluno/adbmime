package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

/**
 * TODO when using adb shell, specify the device:
 * (https://stackoverflow.com/a/14655015/3138238)
 * adb -s 7f1c864e shell
 * or
 * adb -s 192.168.56.101:5555 shell
 *
 */
public interface RemoteInput {
    void send();

    static RemoteInputKey homeButton() {
        return RemoteInputKey.HOME;
    }

    static RemoteInputKey delButton() {
        return RemoteInputKey.DEL;
    }

    static RemoteInputKey enterButton() {
        return RemoteInputKey.ENTER;
    }

    static RemoteInputKey browserButton() {
        return RemoteInputKey.EXPLORER;
    }

    static RemoteInputKey backButton() {
        return RemoteInputKey.BACK;
    }

    static RemoteInputText text(String text) {
        return RemoteInputText.newInstance(text);
    }

    static RemoteInputTap tap(MouseEvent e){
        return RemoteInputTap.newInstance(e);
    }

    static RemoteInputTap tap(RemotePoint p){
        return RemoteInputTap.newInstance(p);
    }

    static RemoteInputTap tap(int x, int y){
        return RemoteInputTap.newInstance(x, y);
    }

    static RemoteInputSwipe swipe(MouseEvent e0, MouseEvent e1){
        return RemoteInputSwipe.newInstance(e0, e1);
    }

    static RemoteInputSwipe swipe(RemotePoint p0, RemotePoint p1){
        return RemoteInputSwipe.newInstance(p0, p1);
    }
}
