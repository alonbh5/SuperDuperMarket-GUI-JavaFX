package course.java.sdm.gui.AddDiscountMenu;

import course.java.sdm.classesForUI.*;
import course.java.sdm.gui.InputPane.GetInputPaneController;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;


public class AddDiscountController {

    @FXML    private StackPane MainStackPane;

    @FXML    private TitledPane StoreTitled;

    @FXML    private ComboBox<StoreInfo> StoreCombo;

    @FXML    private TitledPane DiscountInfoTile;

    @FXML    private ComboBox<ItemInStoreInfo> ItemToBuyCombo;

    @FXML    private RadioButton AllRadio;

    @FXML    private ToggleGroup RadioTypeGroup;

    @FXML    private RadioButton OneRadio;

    @FXML    private TitledPane ItemTile;

    @FXML    private TableView<ItemInStoreInfo> ItemTable;

    @FXML    private TableColumn<ItemInStoreInfo, Long> ItemIdColumn;

    @FXML    private TableColumn<ItemInStoreInfo, String> ItemNameColumn;

    @FXML    private TableColumn<ItemInStoreInfo, String> PayByColumn;

    @FXML    private TableColumn<ItemInStoreInfo, Double> PriceColumn;

    @FXML    private Button ContinueButton;

    @FXML    private Button AmountButton;

    private SimpleBooleanProperty isStoreSelected;
    private SimpleBooleanProperty isTypeSelected;
    private SimpleBooleanProperty isItemToBuySelected;
    private SimpleBooleanProperty isAmountSelected;
    private SimpleBooleanProperty isOfferSelected;

    private MainMenuController MainController = null;
    private DiscountInfo newDiscount;

    public AddDiscountController () {
        isStoreSelected = new SimpleBooleanProperty(false);
        isTypeSelected = new SimpleBooleanProperty(false);
        isItemToBuySelected = new SimpleBooleanProperty(false);
        isAmountSelected = new SimpleBooleanProperty(false);
        isOfferSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        ContinueButton.disableProperty().bind(isOfferSelected.not());
        AmountButton.disableProperty().bind(isItemToBuySelected);

        isTypeSelected.bind(OneRadio.selectedProperty().or(AllRadio.selectedProperty()));

        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayByColumn.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("PriceInStore"));

        DiscountInfoTile.collapsibleProperty().bind(isStoreSelected);
        DiscountInfoTile.setExpanded(false);

        ItemTile.collapsibleProperty().bind(isItemToBuySelected.and(isAmountSelected).and(isTypeSelected));
        ItemTile.setExpanded(false);

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

    public void onCreation(Collection<StoreInfo> stores, MainMenuController mainController) {
        MainController = mainController;

        for (StoreInfo cur : stores)
            StoreCombo.getItems().add(cur);
    }

    @FXML
    void OnContinue(ActionEvent event) {

    }

    @FXML
    void OnStoreSelected(ActionEvent event) {

        if (StoreCombo.getValue() != null) {

            Collection<ItemInStoreInfo> itemsInStore = StoreCombo.getSelectionModel().getSelectedItem().Items;
            ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();

            items.addAll(itemsInStore);
            ItemTable.setItems(items);
            isStoreSelected.setValue(true);
            MainController.PrintMassage("Please Choose Items From Store (Double CLick)...");
        }
    }

    @FXML
    void OnItemToBuyAction(ActionEvent event) {
        if (ItemToBuyCombo.getValue() != null)
            isItemToBuySelected.setValue(true);
    }

    @FXML
    void OnAmountAction(ActionEvent event) {

    }

    private void OnUserPickedItem(ItemInStoreInfo itemPressed)  {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = GetInputPaneController.class.getResource("GetInputPane.fxml");
        fxmlLoader.setLocation(url);
        Pane infoComponent = null;
        try {
            infoComponent = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
        }
        GetInputPaneController InputController = fxmlLoader.getController();
        InputController.OnCreation(false, () -> OnAmountSet(itemPressed,InputController.getAmount()));
        InputController.ChangeTitle("Set Amount You Get");

        MainStackPane.getChildren().get(0).setOpacity(0);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainStackPane.getChildren().add(infoComponent);

    }

    private void CreateBasicDiscount () {
        //todo create here when all is the properties say true - > add listener
    }

    private void OnAmountSet(ItemInStoreInfo itemPressed, Double amount) {
        if (amount <= 0 ) {
            MainController.PrintMassage("Sorry - Amount Should Be Positive..");
        }
        else {
            MainStackPane.getChildren().remove(MainStackPane.getChildren().get(1));

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
            InputController.OnCreation(isDec, () -> OnPriceSet(amount,itemPressed,InputController.getAmount()));
            InputController.ChangeTitle("Set Price Per Unit You Get");

            MainStackPane.getChildren().get(0).setOpacity(0);
            MainStackPane.getChildren().get(0).setDisable(true);
            MainStackPane.getChildren().add(infoComponent);
        }
    }

    private void OnPriceSet(Double amount, ItemInStoreInfo itemPressed, Double pricePerUnit) {
        isOfferSelected.setValue(true);

        //todo create discount before and just add it with "addItemYouGet"
        //create when all is done
    }

}
