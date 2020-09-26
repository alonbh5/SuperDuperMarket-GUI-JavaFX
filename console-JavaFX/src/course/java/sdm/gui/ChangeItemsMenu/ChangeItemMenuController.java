package course.java.sdm.gui.ChangeItemsMenu;

import course.java.sdm.classesForUI.ItemInStoreInfo;
import course.java.sdm.classesForUI.ItemInfo;
import course.java.sdm.classesForUI.StoreInfo;
import course.java.sdm.exceptions.DuplicateItemInStoreException;
import course.java.sdm.exceptions.NegativePriceException;
import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.exceptions.StoreItemNotInSystemException;
import course.java.sdm.gui.InputPane.GetInputPaneController;
import course.java.sdm.gui.MainMenu.MainMenuController;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

public class ChangeItemMenuController {

    @FXML    private StackPane MainStackPane;

    @FXML    private Button OkButton;

    @FXML    private RadioButton ChangeRadio;

    @FXML    private ToggleGroup OptionItems;

    @FXML    private RadioButton DeleteRadio;

    @FXML    private RadioButton AddRadio;

    @FXML    private ComboBox<StoreInfo> StoresComboBox;

    @FXML    private Label MassageLabel;

    private SimpleBooleanProperty isStoreSelected;
    private SimpleBooleanProperty ChoiceSelected;
    private MainMenuController MainController;

    public ChangeItemMenuController() {
        this.isStoreSelected = new SimpleBooleanProperty(false);
        this.ChoiceSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        //isFileSelected.addListener((observable, oldValue, newValue) -> MainMenuController::UploadXML);
        OkButton.disableProperty().bind(Bindings.or(isStoreSelected.not(),ChoiceSelected.not()));
        AddRadio.disableProperty().bind(isStoreSelected.not());
        ChangeRadio.disableProperty().bind(isStoreSelected.not());
        DeleteRadio.disableProperty().bind(isStoreSelected.not());
    }


    public void OnCreation (Collection<StoreInfo> stores,MainMenuController main) {
        for (StoreInfo cur : stores) {
            StoresComboBox.getItems().add(cur);
        }
        this.MainController = main;
    }

    @FXML
    void OnStoreSelected(ActionEvent event) {
        isStoreSelected.setValue(true);
    }

    @FXML
    void OnDeleteStoreRadio(ActionEvent event) {
        ChoiceSelected.setValue(true);
    }

    @FXML
    void OnAddRadio(ActionEvent event) {
        ChoiceSelected.setValue(true);
    }

    @FXML
    void OnChangeRadio(ActionEvent event) {
        ChoiceSelected.setValue(true);
    }


    @FXML
    void OnContinueButton(ActionEvent event) throws IOException {

        StoreInfo curStore = StoresComboBox.getSelectionModel().getSelectedItem();

        if (!AddRadio.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = ShowItemsInStoreController.class.getResource("ShowItemsInStore.fxml");
            fxmlLoader.setLocation(url);
            Parent infoComponent = fxmlLoader.load(url.openStream());
            ShowItemsInStoreController ItemsController = fxmlLoader.getController();
            ItemsController.setItems(curStore.Items, this);


            /*AnchorPane.setLeftAnchor(infoComponent,0.0);
            AnchorPane.setBottomAnchor(infoComponent,0.0);
            AnchorPane.setRightAnchor(infoComponent,0.0);
            AnchorPane.setTopAnchor(infoComponent,0.0);
            AnchorPane x = new AnchorPane(infoComponent);*/

            MainStackPane.getChildren().add(infoComponent);
            MainStackPane.getChildren().get(0).setDisable(true);
            MainStackPane.getChildren().get(0).setOpacity(0);
            MainController.PrintMassage("Double Click On Item");
        }
        else {
            Collection<ItemInfo> Items = null;
            try {
                Items = MainController.getAllItems();
            } catch (NoValidXMLException e) {
                MainController.PrintMassage("Unknown Error! (loading items from system - check XML)");
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = ShowAllItemsController.class.getResource("ShowAllItems.fxml");
            fxmlLoader.setLocation(url);
            Parent infoComponent = fxmlLoader.load(url.openStream());
            ShowAllItemsController ItemsController = fxmlLoader.getController();
            ItemsController.setItems((List<ItemInfo>) Items, this);

            MainStackPane.getChildren().add(infoComponent);
            MainStackPane.getChildren().get(0).setDisable(true);
            MainStackPane.getChildren().get(0).setOpacity(0);
            MainController.PrintMassage("Double Click On Item");
        }




    }

    void onPickedItem(ItemInStoreInfo itemSelected) {

        StoreInfo curStore = StoresComboBox.getSelectionModel().getSelectedItem();

        if (DeleteRadio.isSelected()) {
            if (curStore.Items.size() == 1)
                MainController.PrintMassage("Sorry - There is Only One Item In Store");
            else if (!MainController.isItemOkToDelete(itemSelected))
                MainController.PrintMassage("Sorry - This is the Only Store that Sell it..");
            else {
                String discountInfo = MainController.WillDiscountBeDelete(curStore, itemSelected);
                MainController.DeleteItemFromStore(curStore, itemSelected);
                MainController.PrintMassage(itemSelected.Name + " Was Deleted From Store " + curStore.Name + discountInfo);
            }
            MainController.RestoreItemChange();
        }
        if (ChangeRadio.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = GetInputPaneController.class.getResource("GetInputPane.fxml");
            fxmlLoader.setLocation(url);
            Pane infoComponent = null;
            try {
                infoComponent = fxmlLoader.load(url.openStream());
            } catch (IOException e) {
            }
            GetInputPaneController InputController = fxmlLoader.getController();
            InputController.OnCreation(true, () -> OnChangeFinished(itemSelected,InputController.getAmount(),curStore));
            InputController.ChangeTitle("Please Enter New Price");

            MainStackPane.getChildren().get(0).setOpacity(0);
            MainStackPane.getChildren().get(0).setDisable(true);
            MainStackPane.getChildren().get(1).setOpacity(0);
            MainStackPane.getChildren().get(1).setDisable(true);
            MainStackPane.getChildren().add(infoComponent);
        }



    }

    private void OnChangeFinished(ItemInStoreInfo itemSelected, Double amount, StoreInfo curStore) {

        if (amount <= 0)
            MainController.PrintMassage("Sorry - Price Should Be Positive");
        else {
            MainController.ChangePrice(itemSelected,amount,curStore);
            MainController.PrintMassage(itemSelected.Name + " Was Change at Store " + curStore.Name +" To "+ amount);
        }

        MainController.RestoreItemChange();
        //MainStackPane.getChildren().get(0).setOpacity(1);
        //MainStackPane.getChildren().get(0).setDisable(false);
        //MainStackPane.getChildren().remove(MainStackPane.getChildren().get(1));
    }

    public void onAddItemItem(ItemInfo newItem) {
        StoreInfo curStore = StoresComboBox.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = GetInputPaneController.class.getResource("GetInputPane.fxml");
        fxmlLoader.setLocation(url);
        Pane infoComponent = null;
        try {
            infoComponent = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
        }
        GetInputPaneController InputController = fxmlLoader.getController();
        InputController.OnCreation(true, () -> OnAddFinished(newItem,InputController.getAmount(),curStore));
        InputController.ChangeTitle("Please Enter New Price");

        MainStackPane.getChildren().get(0).setOpacity(0);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainStackPane.getChildren().get(1).setOpacity(0);
        MainStackPane.getChildren().get(1).setDisable(true);
        MainStackPane.getChildren().add(infoComponent);
    }

    private void OnAddFinished(ItemInfo newItem, Double amount, StoreInfo curStore) {
        try {
            MainController.AddItem(newItem, amount, curStore);
            MainController.PrintMassage(newItem.Name + " Was Added To Store " + curStore.Name);
        } catch (
                DuplicateItemInStoreException e) {
            MainController.PrintMassage("Sorry - Item "+newItem.Name+" is Already in Store "+curStore.Name+"- (You Can Use Change Price Option)");

        } catch (
                StoreItemNotInSystemException e) {
            MainController.PrintMassage("Sorry - Unknown Error - Item is Not In System...");

        } catch (
                NegativePriceException e) {
            MainController.PrintMassage("Error - Negative Price ");

        }
        MainController.RestoreItemChange();

    }


}
