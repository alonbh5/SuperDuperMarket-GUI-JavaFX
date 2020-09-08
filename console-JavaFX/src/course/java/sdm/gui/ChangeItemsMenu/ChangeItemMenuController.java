package course.java.sdm.gui.ChangeItemsMenu;

import course.java.sdm.classesForUI.StoreInfo;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.Collection;

public class ChangeItemMenuController {

    @FXML    private Button OkButton;

    @FXML    private RadioButton ChangeRadio;

    @FXML    private ToggleGroup OptionItems;

    @FXML    private RadioButton DeleteRadio;

    @FXML    private RadioButton AddRadio;

    @FXML    private ComboBox<String> StoresComboBox;

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
        ChangeRadio.disableProperty().bind(isStoreSelected.not()); //todo not?
    }


    public void OnCreation (Collection<StoreInfo> stores,MainMenuController main) {
        for (StoreInfo cur : stores) {
            StoresComboBox.getItems().add(cur.StoreID +" - "+ cur.Name);
        }
        this.MainController = main;
    }

    @FXML
    void OnStoreSelected(ActionEvent event) {
        isStoreSelected.setValue(true); //todo check store.....
        /*if main.Can I show Delete botton?
            DeleteRadio.setDisable(false);
            else
            MaasageLabel.setText ("Sorry there only one item...")


         */
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
    void OnContinueButton(ActionEvent event) {

        if (DeleteRadio.isSelected()) {
            //load delete menu with store..
        }
        if (AddRadio.isSelected()) {
            //load add menu with store..
        }
        if (ChangeRadio.isSelected()) {
            //load change menu with store..
        }

    }

}
