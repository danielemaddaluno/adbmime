package it.adbmime;

import it.adbmime.adb.*;
import it.adbmime.view.RemoteInputTableViewRow;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;

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
        iconColumn.setCellFactory(getFlagCell());

        remoteInputsTable.setItems(remoteInputsData);

        // Setting dynamic size
        iconColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.10));
        typeColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.20));
        cmndColumn.prefWidthProperty().bind(remoteInputsTable.widthProperty().multiply(0.70));

        iconColumn.setResizable(false);
        typeColumn.setResizable(false);
        cmndColumn.setResizable(false);

        remoteInputsData.add(RemoteInputTableViewRow.getInstance(RemoteInput.tap(1,2)));

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

    private static final int FLAG_SIZE = 26;

    private static <ROW, T> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getFlagCell() {
        return column -> {
            return new TableCell<ROW, T>() {
                @Override
                protected void updateItem(T emojiUnicode, boolean empty) {
                    super.updateItem(emojiUnicode, empty);

                    String emojiUnicodePng = null;
                    if (emojiUnicode == null || empty) {
                        emojiUnicodePng = "no_flag";
                    } else {
                        emojiUnicodePng = emojiUnicode.toString().trim();
                    }

                    emojiUnicodePng = "it.adbmime.adb.RemoteInputTap";
                    String twemojiUrl = "url(\"/images/type/" + emojiUnicodePng + ".png\")";
                    setStyle("-fx-background-image: " + twemojiUrl + ";"
                            + "-fx-background-repeat: stretch;" + "-fx-background-size: " + FLAG_SIZE + " " + FLAG_SIZE
                            + ";" + "-fx-background-position: center center;");
                }
            };
        };
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