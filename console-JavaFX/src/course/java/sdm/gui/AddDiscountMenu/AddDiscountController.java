package course.java.sdm.gui.AddDiscountMenu;

import course.java.sdm.classesForUI.*;
import course.java.sdm.gui.InputPane.GetInputPaneController;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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

    @FXML    private TextField NameTextField;

    private SimpleBooleanProperty isStoreSelected;
    private SimpleBooleanProperty isTypeSelected;
    private SimpleBooleanProperty isItemToBuySelected;
    private SimpleBooleanProperty isAmountSelected;
    private SimpleBooleanProperty isOfferSelected;
    private SimpleBooleanProperty isNameTyped;
    private SimpleBooleanProperty OpenDisInfo;
    private SimpleBooleanProperty OpenAllItems;

    private MainMenuController MainController = null;
    private DiscountInfo newDiscount;

    public AddDiscountController () {
        isStoreSelected = new SimpleBooleanProperty(false);
        isTypeSelected = new SimpleBooleanProperty(false);
        isItemToBuySelected = new SimpleBooleanProperty(false);
        isAmountSelected = new SimpleBooleanProperty(false);
        isOfferSelected = new SimpleBooleanProperty(false);
        isNameTyped =  new SimpleBooleanProperty(false);
        OpenDisInfo =  new SimpleBooleanProperty(false);
        OpenAllItems  =  new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        ContinueButton.disableProperty().bind(isOfferSelected.not());
        AmountButton.disableProperty().bind(isItemToBuySelected.not());

        isTypeSelected.bind(OneRadio.selectedProperty().or(AllRadio.selectedProperty()));

        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayByColumn.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("PriceInStore"));

        OpenDisInfo.bind(isStoreSelected.and(isNameTyped));

        DiscountInfoTile.disableProperty().bind(OpenDisInfo.not());


        OpenAllItems.bind(isItemToBuySelected.and(isAmountSelected).and(isTypeSelected));

        ItemTile.disableProperty().bind(OpenAllItems.not());


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

        ItemToBuyCombo.disableProperty().bind(isTypeSelected.not());
    }

    public void onCreation(Collection<StoreInfo> stores, MainMenuController mainController) {
        MainController = mainController;

        for (StoreInfo cur : stores)
            StoreCombo.getItems().add(cur);
    }

    @FXML
    void OnContinue(ActionEvent event) {
        MainController.CreateDiscount(newDiscount);
    }

    @FXML
    void OnStoreSelected(ActionEvent event) {

        if (StoreCombo.getValue() != null) {

            ItemToBuyCombo.getItems().addAll(StoreCombo.getValue().Items);

            Collection<ItemInStoreInfo> itemsInStore = StoreCombo.getSelectionModel().getSelectedItem().Items;
            ObservableList<ItemInStoreInfo> items = FXCollections.observableArrayList();

            items.addAll(itemsInStore);
            ItemTable.setItems(items);
            isStoreSelected.setValue(true);
            MainController.PrintMassage("Please Pick Discount Type and Then Item To Buy");
        }
    }

    @FXML
    void OnItemToBuyAction(ActionEvent event) {
        if (ItemToBuyCombo.getValue() != null)
            isItemToBuySelected.setValue(true);
    }

    @FXML
    void OnAmountAction(ActionEvent event) {
        boolean isDec = true;
        if (ItemToBuyCombo.getValue().PayBy.toLowerCase().equals("amount"))
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
        InputController.OnCreation(isDec, () -> CreateBasicDiscount(InputController.getAmount()));
        InputController.ChangeTitle("Set Amount You Need To Buy (Per Unit)");

        MainStackPane.getChildren().get(0).setOpacity(0);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainStackPane.getChildren().add(infoComponent);
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

    private void openItems () {
        ItemTile.setDisable(false);
    }

    private void CreateBasicDiscount (Double amountForItem) {

        if (amountForItem <= 0)
            MainController.PrintMassage("Amount Should Be Positive!");
        else {
            String DisType = "ONE OF";
            if (AllRadio.isSelected())
                DisType = "All OR NOTHING";

            ItemInStoreInfo itemInStore = ItemToBuyCombo.getValue();
            OfferItemInfo itemYouBuy = new OfferItemInfo(itemInStore.serialNumber, itemInStore.Name, itemInStore.PayBy,
                    amountForItem, itemInStore.PriceInStore);

            String name = NameTextField.getText();
            name = name.trim();

            newDiscount = new DiscountInfo(name, DisType, itemYouBuy, amountForItem, null, StoreCombo.getValue().StoreID);
            DiscountInfoTile.disableProperty().unbind();
            DiscountInfoTile.setDisable(true);
            StoreTitled.setDisable(true);
            ItemTile.setExpanded(true);

            MainStackPane.getChildren().get(0).setOpacity(1);
            MainStackPane.getChildren().get(0).setDisable(false);
            MainStackPane.getChildren().remove(MainStackPane.getChildren().get(1));

            isAmountSelected.setValue(true);
        }
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
        newDiscount.addItemYouGet(new OfferItemInfo(itemPressed.serialNumber,itemPressed.Name,itemPressed.PayBy,
                amount,pricePerUnit));

        MainStackPane.getChildren().get(0).setOpacity(1);
        MainStackPane.getChildren().get(0).setDisable(false);
        MainStackPane.getChildren().remove(MainStackPane.getChildren().get(1));
    }

    @FXML
    void OnTextPress(KeyEvent event) {
        if (NameTextField.getText().isEmpty())
            isNameTyped.setValue(false);
        else
            if (NameTextField.getText().trim().isEmpty())
                isNameTyped.setValue(false);
            else
                isNameTyped.setValue(true);
    }

}
