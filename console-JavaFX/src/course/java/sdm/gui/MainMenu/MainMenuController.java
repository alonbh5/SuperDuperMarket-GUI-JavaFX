package course.java.sdm.gui.MainMenu;
import course.java.sdm.classesForUI.*;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.*;
import course.java.sdm.gui.AddDiscountMenu.AddDiscountController;
import course.java.sdm.gui.ChangeItemsMenu.ChangeItemMenuController;
import course.java.sdm.gui.CreateOrderMenu.CreateOrderMenuController;
import course.java.sdm.gui.CustomersMenu.CustomersMenuTileController;
import course.java.sdm.gui.InfoMenuBuiler.InfoMenuController;
import course.java.sdm.gui.OrderMenu.OrderMenuTileController;
import course.java.sdm.gui.ShowItemsMenu.ShowItemsController;
import course.java.sdm.gui.MapMenu.ShowMapController;
import course.java.sdm.gui.StoresMenu.StoresMenuTileController;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Collection;
import java.util.List;


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
    @FXML    private Button AddDiscountButton;
    @FXML    private Label MassageLabel;
    @FXML    private ProgressBar ProgressBar;
    @FXML    private ComboBox<String> SkinComboBox;
    @FXML    private CheckBox AnimationCheckBox;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isXmlLoaded;
    private SimpleBooleanProperty isLoadedDone;


    private Stage primaryStage;
    private SuperDuperMarketSystem MainSDMSystem;


    public MainMenuController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        MainSDMSystem = new SuperDuperMarketSystem(this);
        isXmlLoaded = new SimpleBooleanProperty(false);
        isLoadedDone = new SimpleBooleanProperty(false);
    }

    public OrderInfo getDynamicOrderWithoutDiscounts(List<ItemInOrderInfo> itemsByUser, CustomerInfo selectedUser, Date selectedDate) {
        try {
            return MainSDMSystem.getDynamicOrderInfoBeforeDiscounts(itemsByUser, selectedUser, selectedDate);
        } catch (Exception e) {
            PrintMassage("Unknown Error!");
        }
        return null;
    }

    @FXML
    private void initialize() {
        //isFileSelected.addListener((observable, oldValue, newValue) -> MainMenuController::UploadXML);
        LoadButton.disableProperty().bind(isFileSelected.not().or(isLoadedDone));
        bindAllButtonsToXmlLoaded();
        SkinComboBox.getItems().addAll("Skin 1", "Skin 2", "Skin 3");
    }

    private void bindAllButtonsToXmlLoaded() {
        showStoresButton.disableProperty().bind(isXmlLoaded.not());
        showItemsButton.disableProperty().bind(isXmlLoaded.not());
        showOrdersButton.disableProperty().bind(isXmlLoaded.not());
        showCustomersButton.disableProperty().bind(isXmlLoaded.not());
        NewOrderButton.disableProperty().bind(isXmlLoaded.not());
        ItemUpdateButton.disableProperty().bind(isXmlLoaded.not());
        MapButton.disableProperty().bind(isXmlLoaded.not());
        AddDiscountButton.disableProperty().bind(isXmlLoaded.not());
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
        isLoadedDone.set(false);

        if (MassageLabel.textProperty().isBound()) {
            MassageLabel.textProperty().unbind();
            MassageLabel.setText("Please Load System To Update System");
        } else
            MassageLabel.setText("Please Load System");
    }

    @FXML
    private void UploadXMLAction() {
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
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Massage-Label");
        } catch (Exception e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void bindTaskToUIComponents(Task<Boolean> aTask) {
        // task message
        MassageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        ProgressBar.progressProperty().bind(aTask.progressProperty());

        aTask.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                MassageLabel.getStyleClass().clear();
                MassageLabel.getStyleClass().add("Error-Label");
                ProgressBar.progressProperty().unbind();
            }
        });

        // aTask.setOnSucceeded(e->isXmlLoaded.set(true));
        aTask.setOnSucceeded(e -> {
            isXmlLoaded.set(true);
            isLoadedDone.set(true);
            MassageLabel.textProperty().unbind();
            ProgressBar.progressProperty().unbind();
            MainPane.setCenter(null);

        });

        aTask.setOnFailed(e -> {
            isLoadedDone.set(true);
            MassageLabel.textProperty().unbind();
            ProgressBar.progressProperty().unbind();
        });
    }

    @FXML
    void OnChangeSkin(ActionEvent event) {
        String selection = SkinComboBox.getValue();

        String theme1Url = getClass().getResource("/course/java/sdm/gui/Resources/MainStyle1.css").toExternalForm();
        String theme2Url = getClass().getResource("/course/java/sdm/gui/Resources/MainStyle2.css").toExternalForm();
        String theme3Url = getClass().getResource("/course/java/sdm/gui/Resources/MainStyle3.css").toExternalForm();
        MainPane.getStylesheets().clear();

        switch (selection) {
            case "Skin 1":
                MainPane.getStylesheets().add(theme1Url);
                break;
            case "Skin 2":
                MainPane.getStylesheets().add(theme2Url);
                break;
            case "Skin 3":
                MainPane.getStylesheets().add(theme3Url);
                break;
        }
    }


    @FXML
    void OnCustomersAction(ActionEvent event) throws Exception {

        // load header component and controller from fxml
        MassageLabel.setText("Entered Customer View Menu..");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = CustomersMenuTileController.class.getResource("CustomerMenuTile.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        CustomersMenuTileController CustomerController = fxmlLoader.getController();

        Collection<CustomerInfo> customers = MainSDMSystem.getListOfAllCustomerInSystem();

        CustomerController.setValues(customers);

        MainPane.setCenter(infoComponent);
        //primaryStage.show();
    }

    public void PrintMassage(String str) {
        MassageLabel.setText(str);
    }

    @FXML
    void OnItemsAction(ActionEvent event) throws Exception {
        // load header component and controller from fxml
        MassageLabel.setText("Entered Items View Menu..");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = ShowItemsController.class.getResource("ShowItemsMenu.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        ShowItemsController ItemsController = fxmlLoader.getController();

        Collection<ItemInfo> items = MainSDMSystem.getListOfAllItems();

        ItemsController.setItems(items);

        MainPane.setCenter(infoComponent);

    }

    @FXML
    void OnOrderHistoryAction(ActionEvent event) throws Exception {
        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url;

        MassageLabel.setText("Entered Order History Menu..");
        Collection<OrderInfo> orders = MainSDMSystem.getListOfAllOrderInSystem();

        if (orders.isEmpty()) {
            url = OrderMenuTileController.class.getResource("EmptyOrdersTile.fxml");
            fxmlLoader.setLocation(url);
            Pane Tile = fxmlLoader.load(url.openStream());
            MainPane.setCenter(Tile);
        }
        else {
            url = InfoMenuController.class.getResource("InfoMenu.fxml");
            fxmlLoader.setLocation(url);
            ScrollPane infoComponent = fxmlLoader.load(url.openStream());
            InfoMenuController InfoController = fxmlLoader.getController();

            for (OrderInfo cur : orders)
                InfoController.AddNewOrder(cur);


            MainPane.setCenter(infoComponent);
        }

    }

    @FXML
    void OnNewOrder(ActionEvent event) throws IOException, NoValidXMLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = CreateOrderMenuController.class.getResource("CreateOrderMenu.fxml");
        fxmlLoader.setLocation(url);
        Parent component = fxmlLoader.load(url.openStream());
        CreateOrderMenuController controller = fxmlLoader.getController();
        controller.OnCreation(MainSDMSystem.getListOfAllCustomerInSystem(), MainSDMSystem.getListOfAllStoresInSystem(), this);
        MainPane.setCenter(component);
    }


    @FXML
    void OnStoresAction(ActionEvent event) throws Exception {
        PrintMassage("Entered Stores View Menu..");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();
        ScrollPane items, orders;

        Collection<StoreInfo> stores = MainSDMSystem.getListOfAllStoresInSystem();

        for (StoreInfo cur : stores) {
            items = StoresMenuTileController.getItemsPane(cur);
            orders = StoresMenuTileController.getOrdersPane(cur); //orders = StoresMenuTileController.getOrdersPane(cur);
            InfoController.AddNewStore(cur.StoreID.toString(), cur.Name,
                    cur.getPointString(), cur.PPK.toString(), cur.profitFromShipping.toString(),
                    cur.Discount, items, orders); //File selectedFile = fileChooser.showOpenDialog(primaryStage); for order, dicounts and items... show
        }

        MainPane.setCenter(infoComponent);
    }

    @FXML
    void OnItemUpdate(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = ChangeItemMenuController.class.getResource("ChangeItemMenu.fxml");
        fxmlLoader.setLocation(url);
        Parent component = fxmlLoader.load(url.openStream());
        ChangeItemMenuController controller = fxmlLoader.getController();
        controller.OnCreation(MainSDMSystem.getListOfAllStoresInSystem(), this);
        MainPane.setCenter(component);
    }


    @FXML
    void OnMap(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = ShowMapController.class.getResource("ShowMap.fxml");
        fxmlLoader.setLocation(url);
        Parent component = fxmlLoader.load(url.openStream());
        ShowMapController controller = fxmlLoader.getController();
        int maxRows = MainSDMSystem.getMaxYPoint();
        int maxColumns = MainSDMSystem.getMaxXPoint();
        controller.OnCreation(maxRows,maxColumns);


        Collection<CustomerInfo> customer = MainSDMSystem.getListOfAllCustomerInSystem();
        Collection<StoreInfo> stores = MainSDMSystem.getListOfAllStoresInSystem();

        for (CustomerInfo cur : customer)
            controller.AddItem(cur);
        for (StoreInfo cur : stores)
            controller.AddItem(cur);

        MainPane.setCenter((component));

    }

    public Collection<ItemInfo> getAllItems() throws NoValidXMLException {
        return MainSDMSystem.getListOfAllItems();
    }


    public List<DiscountInfo> getDiscountsStatic(List<ItemInOrderInfo> itemsByUser, StoreInfo storeChosen,CustomerInfo user,Date dateChosen) throws StoreDoesNotSellItemException, OrderIsNotForThisCustomerException, PointOutOfGridException, CustomerNotInSystemException {
        return MainSDMSystem.getDiscountsFromStaticOrder(itemsByUser,storeChosen,user,dateChosen);
    }

    public OrderInfo AddDiscountsToStaticOrder(Collection<DiscountInfo> selectedDiscounts) throws OrderIsNotForThisCustomerException {

        MainSDMSystem.addDiscounts(selectedDiscounts);
        return MainSDMSystem.getTempOrder();
    }

    public void ApproveOrder() {
        try {
            MainSDMSystem.ApproveOrder();
        } catch (OrderIsNotForThisCustomerException e) {
            PrintMassage("Error In Approving Order");
        }
    }

    public void RestoreItemChange() {
        try {
            OnItemUpdate(null);
        } catch (Exception e) {

        }
    }

    public boolean isItemOkToDelete(ItemInStoreInfo itemSelected) {
        return MainSDMSystem.isItemOkToDelete(itemSelected);
    }

    public String WillDiscountBeDelete(StoreInfo curStore, ItemInStoreInfo itemSelected) {
        int x = MainSDMSystem.WillDiscountBeDelete(itemSelected.serialNumber, curStore.StoreID);

        if (x==0)
            return " (No Discount Were Deleted)";
        else
            return " ("+x+" Discounts Were Deleted)";
    }

    public void DeleteItemFromStore(StoreInfo curStore, ItemInStoreInfo itemSelected) {
        try {
            MainSDMSystem.DeleteItemFromStore(itemSelected.serialNumber, curStore.StoreID);
        } catch (Exception e) {
            PrintMassage("Sorry - Unknown Error (DeleteItemFromStore)");
            RestoreItemChange();
        }
    }

    public void ChangePrice(ItemInStoreInfo itemSelected, Double amount, StoreInfo curStore) {
        try {
            MainSDMSystem.ChangePrice(itemSelected.serialNumber, curStore.StoreID, amount);
        } catch (Exception e) {
            PrintMassage("Sorry - Unknown Error (ChangePrice)");
            RestoreItemChange();
        }
    }

    public void AddItem(ItemInfo newItem, Double amount, StoreInfo curStore) throws DuplicateItemInStoreException, StoreItemNotInSystemException, NegativePriceException {
        ItemInStoreInfo newItemToSend = new ItemInStoreInfo (newItem.serialNumber,amount);
            MainSDMSystem.addItemToStore(curStore.StoreID,newItemToSend);

    }

    public Collection<DiscountInfo> getDiscountsDynamic(List<ItemInOrderInfo> itemsByUser, CustomerInfo selectedUser, Date selectedDate) {
        return MainSDMSystem.getDiscountsFromDynamicOrder();
    }

    public OrderInfo AddDiscountsToDynamicOrder (Collection<DiscountInfo> discounts) throws OrderIsNotForThisCustomerException {
        return MainSDMSystem.addDiscounts(discounts);
    }

    public void RestoreNewOrder() {
        try {
            OnNewOrder(null);
        } catch (Exception e) {
            PrintMassage("Sorry - Unknown Error (RestoreNewOrder)");
        }
    }

    public BooleanProperty getAnimation  () {
        return AnimationCheckBox.selectedProperty();
    }

    public DoubleProperty getMassageLocationX() {
        return MassageLabel.layoutXProperty();
    }

    public DoubleProperty getMassageLocationY() {
        return MassageLabel.layoutYProperty();
    }


    @FXML
    void OnAddDiscount(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AddDiscountController.class.getResource("AddDiscount.fxml");
        fxmlLoader.setLocation(url);
        Parent component = fxmlLoader.load(url.openStream());
        AddDiscountController controller = fxmlLoader.getController();
        controller.onCreation(MainSDMSystem.getListOfAllStoresInSystem(), this);
        MainPane.setCenter(component);
    }

    public void CreateDiscount(DiscountInfo newDiscount) {
        try {
            MainSDMSystem.addDiscountToStore(newDiscount.StoreID,newDiscount);
            PrintMassage("Discount Was Added!");
            MainPane.setCenter(null);
        } catch (StoreDoesNotSellItemException e) {
            PrintMassage("Error - Item Does Not Sold In Store");
        } catch (NegativeQuantityException e) {
            PrintMassage("Error - Negative Amount Received: "+e.Quantity);
        } catch (NoOffersInDiscountException e) {
            PrintMassage("Error - No Offers In Discount Received");
        } catch (IllegalOfferException e) {
            PrintMassage("Error - Illegal Offer Received");
        } catch (NegativePriceException e) {
            PrintMassage("Error - Negative Price Received: "+e.PriceReceived);
        }
    }
}
