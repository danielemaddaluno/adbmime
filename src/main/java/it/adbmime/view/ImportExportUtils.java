package it.adbmime.view;

import it.adbmime.AdbMimeController;
import it.adbmime.App;
import it.adbmime.adb.input.RemoteInput;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class ImportExportUtils {
    private ImportExportUtils(){}

    public static final String FILE_PREFIX = "adbmime_";
    public static final String EXTENSION = ".txt";
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

    public static void exportRows(AdbMimeController adbMimeController, ObservableList<RemoteInputTableViewRow> remoteInputsData) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to File:");
        fileChooser.setInitialFileName(FILE_PREFIX + DATE_FORMAT.format(new Date()) + EXTENSION);
        File file = fileChooser.showSaveDialog(App.getPrimaryScene().getWindow());

        if (file != null) {
            System.out.println(file);
            Platform.runLater(() -> {
                adbMimeController.setDisabledForActions(true);
                new Thread(() -> {
                    ImportExportUtils.exportRows(remoteInputsData, file);
                    adbMimeController.setDisabledForActions(false);
                }).start();
            });
        } else {
            System.out.println("Export command cancelled by user.");
        }
    }

    private static void exportRows(ObservableList<RemoteInputTableViewRow> remoteInputsData, File file) {
        List<RemoteInput> remoteInputs = remoteInputsData.stream().map(i -> i.getRemoteInput()).collect(Collectors.toList());
        RemoteInput.toCommands(remoteInputs, file);
    }

    public static void importRows(AdbMimeController adbMimeController, ObservableList<RemoteInputTableViewRow> remoteInputsData) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import from File:");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file);
            Platform.runLater(() -> {
                adbMimeController.setDisabledForActions(true);
                new Thread(() -> {
                    ImportExportUtils.importRows(remoteInputsData, file);
                    adbMimeController.setDisabledForActions(false);
                }).start();
            });
        } else {
            System.out.println("Import command cancelled by user.");
        }
    }

    private static void importRows(ObservableList<RemoteInputTableViewRow> remoteInputsData, File file) {
        remoteInputsData.clear();
        List<RemoteInput> remoteInputs = RemoteInput.fromCommands(file);
        for(RemoteInput remoteInput: remoteInputs){
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
        }
    }

    public static void installApk(AdbMimeController adbMimeController, ObservableList<RemoteInputTableViewRow> remoteInputsData) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Install from apk:");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("APK files (*.apk)", "*.apk")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file);
            Platform.runLater(() -> {
                adbMimeController.setDisabledForActions(true);
                RemoteInput remoteInput = RemoteInput.install(file);
                if(remoteInput != null) {
                    remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
                }
                remoteInput.send();
                adbMimeController.setDisabledForActions(false);
            });
        } else {
            System.out.println("Install command cancelled by user.");
        }
    }
}
