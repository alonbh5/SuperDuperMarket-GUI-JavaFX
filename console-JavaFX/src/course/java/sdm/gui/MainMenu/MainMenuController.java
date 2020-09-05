package course.java.sdm.gui.MainMenu;
import course.java.sdm.classesForUI.*;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.gui.InfoMenuBuiler.InfoMenuController;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MainMenuController {

    @FXML    private BorderPane MainPane;
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
    @FXML    private ComboBox<String> SkinComboBox;
   // @FXML    private Pane CenterPane;

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
        SkinComboBox.getItems().addAll("Skin 1","Skin 2","Skin 3");
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
            //LoadButton.setDisable(true); todo can i do it?
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Massage-Label");
        } catch (Exception e) { //todo this is not working..
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
        }

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask) { //todo this on finsih
        // task message
        MassageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        ProgressBar.progressProperty().bind(aTask.progressProperty());

        aTask.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
            if(newValue != null) {
                MassageLabel.getStyleClass().clear();
                MassageLabel.getStyleClass().add("Error-Label");
                ProgressBar.progressProperty().unbind();
           }
        });

        aTask.setOnSucceeded(e->isXmlLoaded.set(true));

    }

    @FXML
    void OnChangeSkin(ActionEvent event) {
        String selection = SkinComboBox.getValue();

        switch(selection) {
            case "Skin 1":
                MainPane.setId("MainMenuPane1");
                break;
            case "Skin 2":
                MainPane.setId("MainMenuPane2");
                break;
            case "Skin 3":
                MainPane.setId("MainMenuPane3");
                break;
        }
    }


    @FXML
    void OnCustomersAction(ActionEvent event) throws Exception{

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        Collection<CustomerInfo> customers = MainSDMSystem.getListOfAllCustomerInSystem();

        for (CustomerInfo cur : customers) {
            InfoController.AddNewCustomer(cur.ID.toString()
                    ,cur.name
                    ,cur.getLocationString()
                    ,cur.AmountOfOrders.toString()
                    ,cur.AvgPriceForShipping.toString()
                    ,cur.AvgPriceForOrderWithoutShipping.toString());
        }

        MainPane.setCenter(infoComponent);
        //primaryStage.show();
    }

    @FXML
    void OnItemsAction(ActionEvent event) throws Exception {
        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        Collection<ItemInfo> items = MainSDMSystem.getListOfAllItems();

        for (ItemInfo cur : items) {
            InfoController.AddNewItem(cur.serialNumber.toString()
                    ,cur.Name
                    ,cur.PayBy
                    ,cur.NumOfSellingStores.toString()
                    ,cur.AvgPrice.toString()
                    ,cur.NumOfSellingStores.toString());
        }

        MainPane.setCenter(infoComponent);

    }

    @FXML
    void OnOrderHistoryAction(ActionEvent event) throws Exception {
        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        Collection<OrderInfo> orders = MainSDMSystem.getListOfAllOrderInSystem();

        if (orders.isEmpty()){
            //todo
        }
        else {

            for (OrderInfo cur : orders) {

            }

            MainPane.setCenter(infoComponent);
        }

    }

    @FXML
    void OnStoresAction(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        Collection<StoreInfo> stores = MainSDMSystem.getListOfAllStoresInSystem();

        for (StoreInfo cur : stores) {
            InfoController.AddNewStore();
        }

        MainPane.setCenter(infoComponent);
    }


}
