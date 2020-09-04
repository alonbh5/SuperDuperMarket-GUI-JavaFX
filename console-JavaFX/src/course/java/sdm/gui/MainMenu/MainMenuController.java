package course.java.sdm.gui.MainMenu;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class MainMenuController {

    @FXML    private Button XMLButton;
    @FXML    private Button LoadButton;
    @FXML    private Button showStoresButton;
    @FXML    private Button showItemsButton;
    @FXML    private Button showOrdersButton;
    @FXML    private Button showCustomersButton;
    @FXML    private Button NewOrderButton;
    @FXML    private Button ItemUpdateButton;
    @FXML    private Button MapButton;
    @FXML    private Label MassageLabel;
    @FXML    private ProgressBar ProgressBar;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isXmlLoaded;


    private Stage primaryStage;
    private SuperDuperMarketSystem MainSDMSystem;

    public MainMenuController () {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        MainSDMSystem = new SuperDuperMarketSystem(this);
        isXmlLoaded = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        //isFileSelected.addListener((observable, oldValue, newValue) -> MainMenuController::UploadXML);
        LoadButton.disableProperty().bind(isFileSelected.not());
        bindAllButtonsToXmlLoaded();
    }

    private void bindAllButtonsToXmlLoaded() {
        showStoresButton.disableProperty().bind(isXmlLoaded.not());
        showItemsButton.disableProperty().bind(isXmlLoaded.not());
        showOrdersButton.disableProperty().bind(isXmlLoaded.not());
        showCustomersButton.disableProperty().bind(isXmlLoaded.not());
        NewOrderButton.disableProperty().bind(isXmlLoaded.not());
        ItemUpdateButton.disableProperty().bind(isXmlLoaded.not());
        MapButton.disableProperty().bind(isXmlLoaded.not());
    }


    @FXML
    void openFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select SDM System file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
        MassageLabel.setText("Please Load System");
    }

    @FXML
    private void UploadXMLAction () {
       /* businessLogic.collectMetadata(
                totalWords::set,
                totalLines::set,
                () -> {
                    isMetadataCollected.set(true);
                    toggleTaskButtons(false);
                }
        );*/

        try {
            MainSDMSystem.UploadInfoFromXML(selectedFileProperty.getValueSafe());
            isXmlLoaded.set(true);
            //LoadButton.setDisable(true); todo can i do it?
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Massage-Label");
        } catch (Exception e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            ProgressBar.progressProperty().unbind();
        }
        //todo unbound MassageText....
        //MassageLabel.textProperty().unbind();

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask) { //todo this on finsih
        // task message
        MassageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        ProgressBar.progressProperty().bind(aTask.progressProperty());


       /* // task cleanup upon finish
        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });*/
    }
}
