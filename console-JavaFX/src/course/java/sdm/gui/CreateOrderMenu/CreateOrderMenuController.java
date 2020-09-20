package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.gui.ChangeItemsMenu.ChangeItemMenuController;
import course.java.sdm.gui.CreateOrderMenu.ChooseDiscounts.DiscountPickerController;
import course.java.sdm.gui.InputPane.GetInputPaneController;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateOrderMenuController {

    //@FXML    private ComboBox<String> UserCombo;
    @FXML    private ComboBox<CustomerInfo> UserCombo;

    @FXML    private TitledPane DateAndTypeTIle;

    @FXML    private TitledPane UserTitled;

    @FXML    private DatePicker datePicker;

    @FXML    private RadioButton StaticRadio;

    @FXML    private ToggleGroup OrderType;

    @FXML    private RadioButton DynamicRadio;

    @FXML    private TitledPane StoreTile;

    @FXML    private ComboBox<StoreInfo> StoresCombo;

    @FXML    private TitledPane ItemTile;

    @FXML    private TableView<ItemInStoreInfo> ItemTable;

    @FXML    private TableColumn<ItemInStoreInfo, Long> ItemIdColumn;

    @FXML    private TableColumn<ItemInStoreInfo, String> ItemNameColumn;

    @FXML    private TableColumn<ItemInStoreInfo, String> PayByColumn;

    @FXML    private TableColumn<ItemInStoreInfo, Double> PricePerUnitColumn;

    @FXML    private Button ContinueButton;

    @FXML    private StackPane MainStackPane;



    private SimpleBooleanProperty isUserSelected;
    private SimpleBooleanProperty isDateSelected;
    private SimpleBooleanProperty isItemSelected;
    private SimpleBooleanProperty isStaticOrderTypeSelected;
    private SimpleBooleanProperty isDynamicOrderTypeSelected;
    private SimpleBooleanProperty isStoreSelected;
    private SimpleBooleanProperty showStores;
    private SimpleBooleanProperty showAllItems;

    private MainMenuController MainController;
    private List<ItemInOrderInfo> ItemsByUser;
    Collection<DiscountInfo> discounts;


    public CreateOrderMenuController () {
        isUserSelected = new SimpleBooleanProperty(false);
        isDateSelected = new SimpleBooleanProperty(false);
        isStaticOrderTypeSelected = new SimpleBooleanProperty(false);
        isDynamicOrderTypeSelected = new SimpleBooleanProperty(false);
        isStoreSelected = new SimpleBooleanProperty(false);
        showStores = new SimpleBooleanProperty(false);
        showAllItems = new SimpleBooleanProperty(false);
        isItemSelected = new SimpleBooleanProperty(false);
        ItemsByUser = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        showStores.bind(isDateSelected.and(isStaticOrderTypeSelected));
        showStores.addListener(this::exposeStore);

        showAllItems.bind(isDateSelected.and(isDynamicOrderTypeSelected));
        showAllItems.addListener(this::exposeAllItems);

        DateAndTypeTIle.collapsibleProperty().bind(isUserSelected);
        DateAndTypeTIle.setExpanded(false);

        StoreTile.collapsibleProperty().bind(showStores);
        StoreTile.setExpanded(false);

        ItemTile.collapsibleProperty().bind(isStoreSelected.or(isDateSelected.and(isDynamicOrderTypeSelected)));
        ItemTile.setExpanded(false);

        ContinueButton.disableProperty().bind(isItemSelected.not());

        UserTitled.disableProperty().bind(isItemSelected);
        DateAndTypeTIle.disableProperty().bind(isItemSelected);
        StoreTile.disableProperty().bind(isItemSelected);

        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayByColumn.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        PricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("PriceInStore"));

        ItemTable.setRowFactory(tv -> {
            TableRow<ItemInStoreInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ItemInStoreInfo rowData = row.getItem();
                    OnUserPickedItem(rowData);
                }
            });
            return row ;
        });

    }

    private void OnUserPickedItem(ItemInStoreInfo itemPressed)  {

        boolean isDec = true;
        if (itemPressed.PayBy.toLowerCase().equals("amount"))
            isDec=false;


        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = GetInputPaneController.class.getResource("GetInputPane.fxml");
        fxmlLoader.setLocation(url);
        Pane infoComponent = null;
        try {
            infoComponent = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
        }
        GetInputPaneController InputController = fxmlLoader.getController();
        InputController.OnCreation(isDec, () -> OnInputFinished(itemPressed,InputController.getAmount()));

        MainStackPane.getChildren().get(0).setOpacity(0);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainStackPane.getChildren().add(infoComponent);


    }

    private void OnInputFinished(ItemInStoreInfo itemPressed,Double userAmount) {
        //called from input after it's done
        boolean isNew = true;

        for (ItemInOrderInfo cur : ItemsByUser) {
            if (cur.serialNumber.equals(itemPressed.serialNumber)) {
                cur.addAmount(userAmount);
                isNew=false;
                MainController.PrintMassage("Added Amount for "+itemPressed.Name + " new Amount is " + cur.amountBought);
                break;
            }
        }
        if (isNew) {
            ItemsByUser.add(new ItemInOrderInfo(itemPressed,userAmount));
            MainController.PrintMassage("Amount for "+itemPressed.Name + " is " + userAmount);
        }
        isItemSelected.setValue(true);
        MainStackPane.getChildren().get(0).setOpacity(1);
        MainStackPane.getChildren().get(0).setDisable(false);
        MainStackPane.getChildren().remove(MainStackPane.getChildren().get(1));
    }

    private void exposeAllItems(ObservableValue<? extends Boolean> observableValue, Boolean object, Boolean object1)  {
        Collection<ItemInfo> itemsInSystem = null;
        try {
            itemsInSystem = MainController.getAllItems();
        } catch (NoValidXMLException e) {
                MainController.PrintMassage("No Valid XML");
        }
        ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();

        PricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, Double>("off"));

        for (ItemInfo cur : itemsInSystem)
             items.add(new ItemInStoreInfo(cur.serialNumber,cur.Name,cur.PayBy,0,0));

        ItemTable.setItems(items);

        isStoreSelected.setValue(true);
        ItemTile.setExpanded(true);
    }

    private void exposeStore(ObservableValue<? extends Boolean> observableValue, Boolean object, Boolean object1) {
            StoreTile.setExpanded(observableValue.getValue());
            StoresCombo.setDisable(!observableValue.getValue()); //todo stores not Closing...
    }

    public void OnCreation (Collection<CustomerInfo> customer, Collection<StoreInfo> stores, MainMenuController mainController ) {
        MainController = mainController;

        for (CustomerInfo cur : customer)
            UserCombo.getItems().add(cur);

        for (StoreInfo cur : stores)
            StoresCombo.getItems().add(cur);
    }


    @FXML
    void OnStoreSelected(ActionEvent event) {

            if (StoresCombo.getValue() != null) {

                Collection<ItemInStoreInfo> itemsInStore = StoresCombo.getSelectionModel().getSelectedItem().Items;
                ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();
                PricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, Double>("PriceInStore"));

                items.addAll(itemsInStore);
                ItemTable.setItems(items);
                isStoreSelected.setValue(true);
                ItemTile.setExpanded(true);
                MainController.PrintMassage("Static Order - Please Choose Items From Store (Double CLick)...");
            }
    }

    @FXML
    void OnUserSelected(ActionEvent event) {
        isUserSelected.setValue(true);
        DateAndTypeTIle.setExpanded(true);
    }

    @FXML
    void OnDynamicRadio(ActionEvent event) {
        isStaticOrderTypeSelected.setValue(false);
        isDynamicOrderTypeSelected.setValue(true);
        isStoreSelected.setValue(false);

        StoresCombo.setValue(null); //reset the store comboBox..
        StoresCombo.getSelectionModel().clearSelection();

        StoreTile.setExpanded(false); //todo this not works..

        MainController.PrintMassage("Dynamic Order - Please Choose From All Items (Double Click)...");
    }

    @FXML
    void OnStaticRadio(ActionEvent event) {
        isStaticOrderTypeSelected.setValue(true);
        isDynamicOrderTypeSelected.setValue(false);
        MainController.PrintMassage("Static Order - Please Choose Store...");
    }

    @FXML
    void OnDateSelected(ActionEvent event) {
        isDateSelected.setValue(true);
    }

    @FXML
    void OnContinue(ActionEvent event) {
        CustomerInfo SelectedUser = UserCombo.getSelectionModel().getSelectedItem();
        Date SelectedDate = Date.valueOf(datePicker.getValue());
        StoreInfo selectedStore = StoresCombo.getSelectionModel().getSelectedItem();
        if (isStaticOrderTypeSelected.getValue())
            doStaticOrder(selectedStore);
        else
            doDynamicOrder(SelectedUser,SelectedDate);
    }

    private void doDynamicOrder(CustomerInfo selectedUser, Date selectedDate) {
        OrderInfo getOrderSum = MainController.getDynamicOrderWithoutDiscounts(ItemsByUser,selectedUser,selectedDate);

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = StoreSumUpController.class.getResource("StoreSumUp.fxml");
        fxmlLoader.setLocation(url);
        Parent infoComponent = null;
        try {
            infoComponent = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
        }
        StoreSumUpController storeSumUpController = fxmlLoader.getController();
        storeSumUpController.setValues(getOrderSum,this::showDiscount);

        MainStackPane.getChildren().get(0).setOpacity(0);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainStackPane.getChildren().add(infoComponent);
        MainController.PrintMassage("Click Button To Continue..");

        //todo show all store here......
        //Collection<DiscountInfo> discounts = MainController.getDiscounts(ItemsByUser,false,null);
    }

    private void doStaticOrder(StoreInfo selectedStore) {
        CustomerInfo SelectedUser = UserCombo.getSelectionModel().getSelectedItem();
        Date SelectedDate = Date.valueOf(datePicker.getValue());

        try {
            discounts= MainController.getDiscountsStatic(ItemsByUser,selectedStore,SelectedUser,SelectedDate);
            //showDiscount();//todo!@!#!@#
            //discounts.get(0).addAmountWanted();
            //discounts.get(0).addAmountWanted();
            showDiscount();

        } catch (Exception e) {
            MainController.PrintMassage("Unknown Error");
        }

    }

    private void showDiscount() {
        MainStackPane.getChildren().clear();
        CustomerInfo SelectedUser = UserCombo.getSelectionModel().getSelectedItem();
        Date SelectedDate = Date.valueOf(datePicker.getValue());


        try {
            if (!isStaticOrderTypeSelected.getValue())
                discounts = MainController.getDiscountsDynamic(ItemsByUser, SelectedUser, SelectedDate);
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = DiscountPickerController.class.getResource("DiscountPicker.fxml"); //todo make it all in common static..
            fxmlLoader.setLocation(url);
            Parent component = fxmlLoader.load(url.openStream());
            DiscountPickerController controller = fxmlLoader.getController();
            controller.OnCreation(discounts, this::Done);
            //MainStackPane.getChildren().get(0).setOpacity(0);
            //MainStackPane.getChildren().get(0).setDisable(true);
            MainStackPane.getChildren().add(component);
        } catch (Exception e) {
            MainController.PrintMassage("Unknown Error");
        }

    }

    private void Done () {
        try {
            if (isStaticOrderTypeSelected.getValue()) {
                MainController.ApproveStaticOrder(discounts);
                MainController.PrintMassage("Static Order Added To System");
            } else {
                MainController.ApproveDynamicOrder(discounts);
                MainController.PrintMassage("Dynamic Order Added To System");
            }
        }catch (Exception e) {
            MainController.PrintMassage("Unknown Error");
        }
        MainController.RestoreNewOrder();

    }




}
