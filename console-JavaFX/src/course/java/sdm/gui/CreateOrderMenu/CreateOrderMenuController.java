package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.*;
import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateOrderMenuController {

    //@FXML    private ComboBox<String> UserCombo;
    @FXML    private ComboBox<CustomerInfo> UserCombo;

    @FXML    private TitledPane DateAndTypeTIle;

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

    private void OnUserPickedItem(ItemInStoreInfo rowData) {
        //Double userAmount = MainController.getUserAmount(rowData);
        Double userAmount =0.5;
        ItemsByUser.add(new ItemInOrderInfo(rowData,userAmount));
        MainController.PrintMassage("Amount for "+rowData.Name + "is " + userAmount);
        isItemSelected.setValue(true);
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
    void OnContinue(ActionEvent event) {

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




}
