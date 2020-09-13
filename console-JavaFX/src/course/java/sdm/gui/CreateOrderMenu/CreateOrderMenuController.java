package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.CustomerInfo;
import course.java.sdm.classesForUI.ItemInStoreInfo;
import course.java.sdm.classesForUI.ItemInfo;
import course.java.sdm.classesForUI.StoreInfo;
import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.script.Bindings;
import javax.xml.ws.Binding;
import java.util.Collection;

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


    private MainMenuController MainController;
    private SimpleBooleanProperty isUserSelected;
    private SimpleBooleanProperty isDateSelected;
    private SimpleBooleanProperty isStaticOrderTypeSelected;
    private SimpleBooleanProperty isDynamicOrderTypeSelected;
    private SimpleBooleanProperty isStoreSelected;
    private SimpleBooleanProperty showStores;
    private SimpleBooleanProperty showAllItems;



    public CreateOrderMenuController () {
        isUserSelected = new SimpleBooleanProperty(false);
        isDateSelected = new SimpleBooleanProperty(false);
        isStaticOrderTypeSelected = new SimpleBooleanProperty(false);
        isDynamicOrderTypeSelected = new SimpleBooleanProperty(false);
        isStoreSelected = new SimpleBooleanProperty(false);
        showStores = new SimpleBooleanProperty(false);
        showAllItems = new SimpleBooleanProperty(false);
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

        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, Long>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, String>("Name"));
        PayByColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, String>("PayBy"));
        PricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<ItemInStoreInfo, Double>("PriceInStore"));
    }

    private void exposeAllItems(ObservableValue<? extends Boolean> observableValue, Boolean object, Boolean object1)  {
        Collection<ItemInfo> itemsInSystem = null;
        try {
            itemsInSystem = MainController.getAllItems();
        } catch (NoValidXMLException e) {

        }
        ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();

        for (ItemInfo cur : itemsInSystem)
             items.add(new ItemInStoreInfo(cur.serialNumber,cur.Name,cur.PayBy,0,0));

        ItemTable.setItems(items);

        isStoreSelected.setValue(true);
        ItemTile.setExpanded(true);
    }

    private void exposeStore(ObservableValue<? extends Boolean> observableValue, Boolean object, Boolean object1) {
            StoreTile.setExpanded(observableValue.getValue());
            StoresCombo.setDisable(!observableValue.getValue());
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
        Collection<ItemInStoreInfo> itemsInStore = StoresCombo.getSelectionModel().getSelectedItem().Items;
        ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();
        items.addAll(itemsInStore);
        ItemTable.setItems(items);

        isStoreSelected.setValue(true);
        ItemTile.setExpanded(true);
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
    }

    @FXML
    void OnStaticRadio(ActionEvent event) {
        isStaticOrderTypeSelected.setValue(true);
        isDynamicOrderTypeSelected.setValue(false);
    }

    @FXML
    void OnDateSelected(ActionEvent event) {
        isDateSelected.setValue(true);
    }




}
