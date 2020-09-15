package course.java.sdm.gui.ChangeItemsMenu;

import course.java.sdm.classesForUI.ItemInStoreInfo;
import course.java.sdm.classesForUI.ItemInfo;
import course.java.sdm.classesForUI.StoreInfo;
import course.java.sdm.gui.MainMenu.MainMenuController;
import course.java.sdm.gui.ShowItemsMenu.ShowItemsController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

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
        isStoreSelected.setValue(true); //todo check store.....
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

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = ShowItemsInStoreController.class.getResource("ShowItemsInStore.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        Parent infoComponent = fxmlLoader.load(url.openStream());
        ShowItemsInStoreController ItemsController = fxmlLoader.getController();
        ItemsController.setItems(curStore.Items,this);

        MainStackPane.getChildren().add(infoComponent);
        MainStackPane.getChildren().get(0).setDisable(true);
        MainController.PrintMassage("Double Click On Item");



    }

    void onPickedItem(ItemInStoreInfo itemSelected) {

        StoreInfo curStore = StoresComboBox.getSelectionModel().getSelectedItem();

        if (DeleteRadio.isSelected()) {
            if (curStore.Items.size() == 1)
                MainController.PrintMassage("Sorry - There is Only One Item In Store");
            else if (!MainController.isItemOkToDelete(itemSelected))
                MainController.PrintMassage("Sorry - This is the Only Store that Sell it..");
            else {
                MainController.DeleteItemFromStore(curStore, itemSelected);
                MainController.PrintMassage(itemSelected.Name + " Was Deleted From Store " + curStore.Name);
            }
        }
        if (AddRadio.isSelected()) {
            //load add menu with store..
        }
        if (ChangeRadio.isSelected()) {
            //load change menu with store..
        }

        MainController.RestoreItemChange();

    }

}
