package it.adbmime;

import it.adbmime.adb.*;
import it.adbmime.view.RemoteInputTableViewRow;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdbMimeController {
    private DeviceTap deviceTap;
    @FXML
    private ChoiceBox<RemoteInputKey> inputKeyChoiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField textField;
    @FXML
    private StackPane stackPaneForImage;
    @FXML
    private Spinner<Integer> replayCommandsSleepSpinner;
    @FXML
    private Button replayCommandsButton;

    @FXML
    private TableView<RemoteInputTableViewRow> remoteInputsTable;
    private ObservableList<RemoteInputTableViewRow> remoteInputsData = FXCollections.observableArrayList();
    /**
     * Icon
     */
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> iconColumn;
    /**
     * RemoteInputType
     */
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> typeColumn;
    /**
     * Description
     */
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> cmndColumn;



    @FXML
    protected void initialize() {
//        DeviceScreenSize deviceScreenSize = DeviceOutput.getScreenSize();

        inputKeyChoiceBox.getItems().addAll(RemoteInputKey.values());
        inputKeyChoiceBox.setValue(RemoteInputKey.HOME);

        // https://stackoverflow.com/a/12635224/3138238
        // https://stackoverflow.com/questions/49820196/javafx-resize-imageview-to-anchorpane
        imageView.fitWidthProperty().bind(stackPaneForImage.widthProperty().subtract(10));
        imageView.fitHeightProperty().bind(stackPaneForImage.heightProperty().subtract(10));

        onScreenUpdateButtonClick();
//        Observable.interval(10, TimeUnit.SECONDS, JavaFxScheduler.platform()).map(next -> DeviceOutput.getScreenCapture()).map(DeviceScreenCapture::getImage).subscribe(imageView::setImage);

        iconColumn.setCellValueFactory(cellData -> cellData.getValue().getIconProp());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProp());
        cmndColumn.setCellValueFactory(cellData -> cellData.getValue().getCmndProp());
        iconColumn.setCellFactory(getIconCell());

        remoteInputsTable.setItems(remoteInputsData);

        // Setting dynamic size
        iconColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.10));
        typeColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.20));
        cmndColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.70));

        iconColumn.setResizable(false);
        typeColumn.setResizable(false);
        cmndColumn.setResizable(false);

        iconColumn.setSortable(false);
        typeColumn.setSortable(false);
        cmndColumn.setSortable(false);

        // Delete button listener
        remoteInputsTable.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE) || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                deleteRemoteInput();
            }
        });

//        economicEventsTable.setRowFactory(tableView -> {
//            TableRow<EconomicEvent> row = new TableRow<>();
//            ChangeListener<String> changeListener = (obs, oldTitle, newTitle) -> {
//                ColorString.setRowColor(rowColors, row, newTitle);
//            };
//
//            row.itemProperty().addListener((obs, previousEe, currentEe) -> {
//                if (previousEe != null) {
//                    previousEe.getTitleProp().removeListener(changeListener);
//                }
//                if (currentEe != null) {
//                    currentEe.getTitleProp().addListener(changeListener);
//                    String title = currentEe.getTitle();
//                    ColorString.setRowColor(rowColors, row, title);
//                } else {
//                    ColorString.setRowColor(rowColors, row, null);
//                }
//            });
//            return row;
//        });

    }

    private void deleteRemoteInput() {
        ObservableList<RemoteInputTableViewRow> selectedRows = remoteInputsTable.getSelectionModel().getSelectedItems();
        List<RemoteInputTableViewRow> rows = new ArrayList<>(selectedRows);
        rows.forEach(row -> remoteInputsTable.getItems().remove(row));
    }

    private static final int FLAG_SIZE = 20;

    /**
     * https://edencoding.com/resources/css_properties/url/
     * @param <ROW>
     * @param <T>
     * @return
     */
    private static <ROW, T> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getIconCell() {
        return column -> new TableCell<ROW, T>() {
            @Override
            protected void updateItem(T type, boolean empty) {
                super.updateItem(type, empty);

                String typePng = null;
                if (type == null || empty) {
                    typePng = "no_type";
                } else {
                    typePng = type.toString().trim();
                }
                String imageUrl = AdbMimeController.class.getResource("/images/type/" + typePng + ".png").toExternalForm();
                setStyle("-fx-background-image: url('" + imageUrl + "');" + ";"
                        + "-fx-background-repeat: stretch;"
                        + "-fx-background-size: " + FLAG_SIZE + " " + FLAG_SIZE + ";"
                        + "-fx-background-position: center center;");
            }
        };
    }

    @FXML
    protected void onReplayCommandsButtonClick(){
        new Thread(() -> {
            replayCommandsButton.setDisable(true);
            replayCommandsSleepSpinner.setDisable(true);
            for(RemoteInputTableViewRow row: remoteInputsTable.getItems()){
                row.getRemoteInput().send();
                try {
                    Thread.sleep(1000*replayCommandsSleepSpinner.getValue());
                } catch (InterruptedException e) {
                }
            }
            replayCommandsButton.setDisable(false);
            replayCommandsSleepSpinner.setDisable(false);
        }).start();
    }

    @FXML
    private void openAbout() throws IOException {
        App.setRoot("about");
    }

    @FXML
    protected void onInputKeyButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(inputKeyChoiceBox.getValue().send()));
    }

    @FXML
    protected void onHomeButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.homeButton().send()));
    }

    @FXML
    protected void onBackButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.homeButton().send()));
        RemoteInput.backButton().send();
    }

    @FXML
    protected void onOpenBrowserButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.browserButton().send()));
    }

    @FXML
    protected void onDeleteButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.delButton().send()));
    }

    @FXML
    protected void onEnterButtonClick() {
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.enterButton().send()));
    }

    @FXML
    protected void onScreenUpdateButtonClick() {
        Platform.runLater(() -> {
            DeviceScreenCapture screen = DeviceOutput.getScreenCapture();
            imageView.setImage(screen.getImage());
        });
    }

    @FXML
    protected void onCaptureTapButtonClick() {
        Platform.runLater(() -> {
            this.deviceTap = DeviceOutput.getTap();
            System.out.println(deviceTap + "\n");
        });
    }

    @FXML
    protected void onReplayTapButtonClick() {
        Platform.runLater(() -> {
            if(deviceTap != null){
                int x = deviceTap.getX();
                int y = deviceTap.getY();
                RemoteInput.tap(x, y).send();
            }
        });
    }

    @FXML
    public void textFieldAction(ActionEvent ae){
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.text(textField.getText()).send()));
    }

    private MouseEvent lastMousePressed;

    @FXML
    public void onMousePressedAction(MouseEvent e){
        this.lastMousePressed = e;
    }

    @FXML
    public void onMouseReleasedAction(MouseEvent e){
        if(lastMousePressed.getX() == e.getX() && lastMousePressed.getY() == e.getY()){
            RemoteInputTap remoteInputTap = RemoteInput.tap(e);
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInputTap.send()));
        } else {
            RemoteInputSwipe remoteInputSwipe = RemoteInput.swipe(lastMousePressed, e);
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInputSwipe.send()));
        }
    }
}