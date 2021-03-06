package it.adbmime.demo;

import it.adbmime.demo.images.ImageUtils;
import it.adbmime.demo.view.DeviceTableViewRow;
import it.adbmime.demo.view.ImportExportUtils;
import it.adbmime.demo.view.RemoteInputTableViewRow;
import it.adbmime.demo.view.RemotePointJavaFx;
import it.adbmime.devices.DeviceConnect;
import it.adbmime.devices.DeviceDisconnect;
import it.adbmime.devices.DevicesList;
import it.adbmime.input.*;
import it.adbmime.output.DeviceOutput;
import it.adbmime.output.DeviceScreenCapture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdbMimeController {
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
    private TitledPane inputActionsTitlePane1;
    @FXML
    private TitledPane inputActionsTitlePane2;
    @FXML
    private GridPane gridPaneButtonsTop;
    @FXML
    private GridPane gridPaneButtonsBottom;

    @FXML
    private TextField textFieldPackageName;

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
    private Button replayOnAllDevicesCommandsButton;
    @FXML
    private Spinner<Integer> replayCommandsSleepSpinner;

    @FXML
    private Spinner<Integer> ip1Spinner;
    @FXML
    private Spinner<Integer> ip2Spinner;
    @FXML
    private Spinner<Integer> ip3Spinner;
    @FXML
    private Spinner<Integer> ip4Spinner;
    @FXML
    private Spinner<Integer> portSpinner;

    @FXML
    private Button appButtonInstall;
    @FXML
    private Button appButtonOpen;
    @FXML
    private Button appButtonUnInstall;
    @FXML
    private Button appButtonHide;
    @FXML
    private Button appButtonUnHide;


    @FXML
    private TableView<DeviceTableViewRow> devicesTable;
    private ObservableList<DeviceTableViewRow> devicesData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<DeviceTableViewRow, String> devicesStatusColumn;
    @FXML
    private TableColumn<DeviceTableViewRow, String> devicesIdColumn;

    private RemoteInput send(RemoteInput remoteInput) {
        DeviceTableViewRow deviceRow = devicesTable.getSelectionModel().getSelectedItem();
        if(deviceRow == null){
            return remoteInput.send();
        } else {
            return remoteInput.send(deviceRow.getDevice().getId());
        }
    }

    @FXML
    protected void initialize() {
        // Configure inputKeyChoiceBox list
        inputKeyChoiceBox.getItems().addAll(RemoteInputKeycode.values());
        inputKeyChoiceBox.setValue(RemoteInputKeycode.HOME);

        // Configure device ImageView Size and load image
        // https://stackoverflow.com/a/12635224/3138238
        // https://stackoverflow.com/questions/49820196/javafx-resize-imageview-to-anchorpane
        imageView.fitWidthProperty().bind(stackPaneForImage.widthProperty().subtract(10));
        imageView.fitHeightProperty().bind(stackPaneForImage.heightProperty().subtract(10));
        onScreenUpdateButtonClick();

        // Resize title panes
        inputActionsTitlePane1.prefWidthProperty().bind(remoteInputsTable.widthProperty().divide(2).subtract(5));
        inputActionsTitlePane2.prefWidthProperty().bind(remoteInputsTable.widthProperty().divide(2).subtract(5));

        // Setup Table View for Devices
        setupTableViewForDevices();

        // Setup Table View for Remote Inputs
        setupTableViewForRemoteInputs();

        // Enable disable app buttons based on textFieldPackageName property
        textFieldPackageName.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            boolean disable = !(newValue != null && !newValue.isEmpty());
            appButtonOpen.setDisable(disable);
            appButtonUnInstall.setDisable(disable);
            appButtonHide.setDisable(disable);
            appButtonUnHide.setDisable(disable);
        });
    }

    private void setupTableViewForDevices(){
        devicesStatusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProp());
        devicesIdColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProp());
        devicesStatusColumn.setCellFactory(getIconCellDevice());

        devicesTable.setItems(devicesData);

        // Setting dynamic size
        devicesStatusColumn.prefWidthProperty().bind(devicesTable.widthProperty().multiply(0.10));
        devicesIdColumn.prefWidthProperty().bind(devicesTable.widthProperty().multiply(0.9));

        devicesStatusColumn.setResizable(false);
        devicesIdColumn.setResizable(false);
        devicesStatusColumn.setSortable(false);
        devicesIdColumn.setSortable(true);

//        // Delete button listener
//        devicesTable.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode().equals(KeyCode.DELETE) || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
//                deleteTableRow2();
//            }
//        });
    }

    private void setupTableViewForRemoteInputs() {
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

    private static final int ICON_SIZE = 20;

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
                        + "-fx-background-size: " + ICON_SIZE + " " + ICON_SIZE + ";"
                        + "-fx-background-position: center center;");
            }
        };
    }

    private static <ROW, T> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getIconCellDevice() {
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
                String imageUrl = AdbMimeController.class.getResource("/images/status/" + typePng + ".png").toExternalForm();
                setStyle("-fx-background-image: url('" + imageUrl + "');" + ";"
                        + "-fx-background-repeat: stretch;"
                        + "-fx-background-size: " + ICON_SIZE + " " + ICON_SIZE + ";"
                        + "-fx-background-position: center center;");
            }
        };
    }

    public void setDisabledForActions(boolean disabled){
        replayCommandsSleepSpinner.setDisable(disabled);
        replayOnAllDevicesCommandsButton.setDisable(disabled);
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
                send(row.getRemoteInput());

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
    protected void onReplayOnAllDevicesCommandsButtonClick(){
        remoteInputsTable.requestFocus();
        new Thread(() -> {
            setDisabledForActions(true);
            for(RemoteInputTableViewRow row: remoteInputsTable.getItems()){
                remoteInputsTable.getSelectionModel().select(row);

                devicesData.parallelStream().forEach(
                        device -> {
                            row.getRemoteInput().send(device.getDevice().getId());
                        }
                );

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

    private static final long LONG_PRESS_THRESHOLD = 1500;
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

        boolean longPress = System.currentTimeMillis() - startTime > LONG_PRESS_THRESHOLD;
        RemoteInputKey key = RemoteInput.key(longPress, keycode);
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(send(key)));
    }

    @FXML
    public void onKeyReleasedActionChoiceBox(MouseEvent event){
        boolean longPress = System.currentTimeMillis() - startTime > LONG_PRESS_THRESHOLD;
        RemoteInputKey key = RemoteInput.key(longPress, inputKeyChoiceBox.getValue().getKeycode());
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(send(key)));
    }

    @FXML
    protected void onScreenUpdateButtonClick() {
        new Thread(() -> {
            DeviceTableViewRow deviceRow = devicesTable.getSelectionModel().getSelectedItem();
            DeviceScreenCapture screen;
            if(deviceRow == null){
                screen = DeviceOutput.getScreenCapture();
            } else {
                screen = DeviceOutput.getScreenCapture(deviceRow.getDevice().getId());
            }

            Image image = ImageUtils.image(screen.getImage());
            imageView.setImage(image);
        }).start();
    }

    @FXML
    public void textFieldAction(ActionEvent ae){
        remoteInputsData.add(RemoteInputTableViewRow.getInstance(send(RemoteInput.text(textField.getText()))));
    }

    private MouseEvent lastMousePressed;

    @FXML
    public void onMousePressedAction(MouseEvent e){
        this.lastMousePressed = e;
    }

    @FXML
    public void onMouseReleasedAction(MouseEvent e){
        if(lastMousePressed.getX() == e.getX() && lastMousePressed.getY() == e.getY()){
            RemotePoint p0 = RemotePointJavaFx.newInstance(e);
            RemoteInputTap remoteInputTap = RemoteInput.tap(p0);
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(send(remoteInputTap)));
        } else {
            RemotePoint p0 = RemotePointJavaFx.newInstance(lastMousePressed);
            RemotePoint p1 = RemotePointJavaFx.newInstance(e);
            RemoteInputSwipe remoteInputSwipe = RemoteInput.swipe(p0, p1);
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(send(remoteInputSwipe)));
        }
    }

//    @FXML
//    protected void onCaptureTapButtonClick() {
//        new Thread(() -> {
//            this.deviceTap = DeviceOutput.getTap();
//            System.out.println(deviceTap + "\n");
//        }).start();
//    }
//
//    @FXML
//    protected void onReplayTapButtonClick() {
//        new Thread(() -> {
//            if(deviceTap != null){
//                int x = deviceTap.getX();
//                int y = deviceTap.getY();
//                RemoteInput.tap(x, y).send();
//            }
//        }).start();
//    }

    @FXML
    private void onInstallApp() {
        ImportExportUtils.installApk(this, remoteInputsData);
    }

    @FXML
    private void onOpenApp() {
        String packageName = textFieldPackageName.getText();
        if(packageName != null && !packageName.isEmpty()){
            RemoteInput remoteInput = send(RemoteInput.open(packageName));
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
        }
    }

    @FXML
    private void onHideApp() {
        String packageName = textFieldPackageName.getText();
        if(packageName != null && !packageName.isEmpty()){
            RemoteInput remoteInput = send(RemoteInput.hide(packageName));
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
        }
    }

    @FXML
    private void onUnHideApp() {
        String packageName = textFieldPackageName.getText();
        if(packageName != null && !packageName.isEmpty()){
            RemoteInput remoteInput = send(RemoteInput.unhide(packageName));
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
        }
    }

    @FXML
    private void onUninstallApp() {
        String packageName = textFieldPackageName.getText();
        if(packageName != null && !packageName.isEmpty()){
            RemoteInput remoteInput = send(RemoteInput.uninstall(packageName));
            remoteInputsData.add(RemoteInputTableViewRow.getInstance(remoteInput));
        }
    }

    private String getIpPort(){
        int ip1 = ip1Spinner.getValue();
        int ip2 = ip2Spinner.getValue();
        int ip3 = ip3Spinner.getValue();
        int ip4 = ip4Spinner.getValue();
        int port = portSpinner.getValue();
        return ip1 + "." + ip2 + "." + ip3 + "." + ip4 + ":" + port;
    }

    @FXML
    private void onListDevices() {
        System.out.println(getIpPort());
        DevicesList devicesList = DevicesList.newInstance();
        List<DeviceTableViewRow> devices = devicesList.getDevices()
                .stream()
                .map(d -> DeviceTableViewRow.getInstance(d))
                .collect(Collectors.toList());
        devicesData.clear();
        devicesData.addAll(devices);
    }

    @FXML
    private void onConnectDevices() {
        int ip1 = ip1Spinner.getValue();
        int ip2 = ip2Spinner.getValue();
        int ip3 = ip3Spinner.getValue();
        int ip4 = ip4Spinner.getValue();
        int port = portSpinner.getValue();

        DeviceConnect.connect(ip1, ip2, ip3, ip4, port);
        onListDevices();
    }

    @FXML
    private void onDisconnectDevices() {
        DeviceDisconnect.disconnect();
        onListDevices();
    }

}