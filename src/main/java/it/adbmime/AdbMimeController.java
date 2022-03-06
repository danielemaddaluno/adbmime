package it.adbmime;

import it.adbmime.adb.*;
import it.adbmime.view.ImportExportUtils;
import it.adbmime.view.RemoteInputTableViewRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdbMimeController {
    private DeviceTap deviceTap;
    @FXML
    private ChoiceBox<RemoteInputKeycode> inputKeyChoiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField textField;
    @FXML
    private StackPane stackPaneForImage;

    @FXML
    private TableView<RemoteInputTableViewRow> remoteInputsTable;
    private ObservableList<RemoteInputTableViewRow> remoteInputsData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> iconColumn;
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> typeColumn;
    @FXML
    private TableColumn<RemoteInputTableViewRow, String> cmndColumn;

    @FXML
    private TitledPane tableRowsActionsTitlePane;
    @FXML
    private TitledPane replayActionsTitlePane;

    @FXML
    private Button deleteTableRowsButton;
    @FXML
    private Button deleteTableRowButton;
    @FXML
    private Button exportTableRowsButton;
    @FXML
    private Button importTableRowsButton;
    @FXML
    private Button replayCommandsButton;
    @FXML
    private Spinner<Integer> replayCommandsSleepSpinner;


    @FXML
    protected void initialize() {
//        DeviceScreenSize deviceScreenSize = DeviceOutput.getScreenSize();

        inputKeyChoiceBox.getItems().addAll(RemoteInputKeycode.values());
        inputKeyChoiceBox.setValue(RemoteInputKeycode.HOME);

        // https://stackoverflow.com/a/12635224/3138238
        // https://stackoverflow.com/questions/49820196/javafx-resize-imageview-to-anchorpane
        imageView.fitWidthProperty().bind(stackPaneForImage.widthProperty().subtract(10));
        imageView.fitHeightProperty().bind(stackPaneForImage.heightProperty().subtract(10));

        onScreenUpdateButtonClick();
//        Observable.interval(10, TimeUnit.SECONDS, JavaFxScheduler.platform()).map(next -> DeviceOutput.getScreenCapture()).map(DeviceScreenCapture::getImage).subscribe(imageView::setImage);
//        Observable.interval(2, TimeUnit.SECONDS, JavaFxScheduler.platform()).subscribe(tick -> new Thread(() -> imageView.setImage(DeviceOutput.getScreenCapture().getImage())).start());

        setupTableView();
    }

    private void setupTableView() {
        iconColumn.setCellValueFactory(cellData -> cellData.getValue().getIconProp());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProp());
        cmndColumn.setCellValueFactory(cellData -> cellData.getValue().getCmndProp());
        iconColumn.setCellFactory(getIconCell());

        remoteInputsTable.setItems(remoteInputsData);

        // Setting dynamic size
        iconColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.10));
        typeColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.20));
        cmndColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.695));

        iconColumn.setResizable(false);
        typeColumn.setResizable(false);
        cmndColumn.setResizable(false);

        iconColumn.setSortable(false);
        typeColumn.setSortable(false);
        cmndColumn.setSortable(false);

        // Delete button listener
        remoteInputsTable.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE) || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                deleteTableRow();
            }
        });

        tableRowsActionsTitlePane.prefWidthProperty().bind(remoteInputsTable.widthProperty().divide(3).multiply(2).subtract(10));
        replayActionsTitlePane.prefWidthProperty().bind(remoteInputsTable.widthProperty().divide(3));

//        remoteInputsTable.setRowFactory(tableView -> {
//            TableRow<RemoteInputTableViewRow> row = new TableRow<>();
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

    @FXML
    private void exportTableRows() {
        ImportExportUtils.exportRows(this, remoteInputsData);
    }

    @FXML
    private void importTableRows() {
        ImportExportUtils.importRows(this, remoteInputsData);
    }

    @FXML
    private void installApk() {
        ImportExportUtils.installApk(this, remoteInputsData);
    }

    @FXML
    private void deleteTableRows() {
        remoteInputsData.clear();
    }

    @FXML
    private void deleteTableRow() {
        if(!deleteTableRowButton.isDisable()){ // it's important when the replay is running to disable deletion
            ObservableList<RemoteInputTableViewRow> selectedRows = remoteInputsTable.getSelectionModel().getSelectedItems();
            List<RemoteInputTableViewRow> rows = new ArrayList<>(selectedRows);
            rows.forEach(row -> remoteInputsTable.getItems().remove(row));
        }
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

    public void setDisabledForActions(boolean disabled){
        replayCommandsSleepSpinner.setDisable(disabled);
        replayCommandsButton.setDisable(disabled);
        deleteTableRowsButton.setDisable(disabled);
        deleteTableRowButton.setDisable(disabled);
        exportTableRowsButton.setDisable(disabled);
        importTableRowsButton.setDisable(disabled);
    }
    @FXML
    protected void onReplayCommandsButtonClick(){
        remoteInputsTable.requestFocus();
        new Thread(() -> {
            setDisabledForActions(true);
            for(RemoteInputTableViewRow row: remoteInputsTable.getItems()){
                remoteInputsTable.getSelectionModel().select(row);
                row.getRemoteInput().send();
                try {
                    Thread.sleep(1000*replayCommandsSleepSpinner.getValue());
                } catch (InterruptedException e) {
                }
            }
            remoteInputsTable.getSelectionModel().clearSelection();
            setDisabledForActions(false);
        }).start();
    }

    @FXML
    private void openAbout() throws IOException {
        App.setRoot("about");
    }

    private static final long LONGPRESS_THRESHOLD = 2000;
    private long startTime;

    @FXML
    public void onKeyPressedAction(){
        this.startTime = System.currentTimeMillis();;
    }

    @FXML
    public void onKeyReleasedActionSimple(MouseEvent event) {
        Node node = (Node) event.getSource() ;
        String data = (String) node.getUserData();
        int keycode = Integer.parseInt(data);

        boolean longPress = System.currentTimeMillis() - startTime > LONGPRESS_THRESHOLD;
        RemoteInputKey key = RemoteInput.key(longPress, keycode);
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(key.send()));
    }

    @FXML
    public void onKeyReleasedActionChoiceBox(MouseEvent event){
        boolean longPress = System.currentTimeMillis() - startTime > LONGPRESS_THRESHOLD;
        RemoteInputKey key = RemoteInput.key(longPress, inputKeyChoiceBox.getValue().getKeycode());
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(key.send()));
    }

    @FXML
    protected void onScreenUpdateButtonClick() {
        new Thread(() -> {
            DeviceScreenCapture screen = DeviceOutput.getScreenCapture();
            imageView.setImage(screen.getImage());
        }).start();
    }

    @FXML
    protected void onCaptureTapButtonClick() {
        new Thread(() -> {
            this.deviceTap = DeviceOutput.getTap();
            System.out.println(deviceTap + "\n");
        }).start();
    }

    @FXML
    protected void onReplayTapButtonClick() {
        new Thread(() -> {
            if(deviceTap != null){
                int x = deviceTap.getX();
                int y = deviceTap.getY();
                RemoteInput.tap(x, y).send();
            }
        }).start();
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