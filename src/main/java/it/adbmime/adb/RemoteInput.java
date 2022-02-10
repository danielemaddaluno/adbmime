package it.adbmime.adb;

import javafx.scene.input.MouseEvent;

public interface RemoteInput {
    void send();

    static RemoteInputKey homeButton() {
        return RemoteInputKey.HOME;
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

    static RemoteInputTap tap(int x, int y){
        return RemoteInputTap.newInstance(x, y);
    }
}
