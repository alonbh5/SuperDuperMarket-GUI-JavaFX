package course.java.sdm.gui.MainMenu;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainMenuController {

    @FXML    private Button XMLButton;
    @FXML    private Button showStoresButton;
    @FXML    private Button showItemsButton;
    @FXML    private Button showOrdersButton;
    @FXML    private Button showCustomersButton;
    @FXML    private Button NewOrderButton;
    @FXML    private Button ItemUpdateButton;
    @FXML    private Button MapButton;
    @FXML    private Label MassageLabel;

    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;

    private Stage primaryStage;
    private SuperDuperMarketSystem MainSDMSystem;

    @FXML
    private void initialize() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        //isFileSelected.addListener((observable, oldValue, newValue) -> MainMenuController::UploadXML);
        MainSDMSystem = new SuperDuperMarketSystem();
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
        UploadXML();
    }

    private void UploadXML () {
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
            MassageLabel.setText("Xml loaded successful! System Unlocked!");
        } catch (DuplicatePointOnGridException e) { //todo merge this
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains 2 points at the same location " + e.PointInput.toString() + " !");
        } catch (DuplicateItemIDException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains 2 Items with the same id : " + e.id);
        } catch (DuplicateItemInStoreException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains 2 Items with the same id at one store: " + e.id);
        } catch (DuplicateStoreInSystemException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains 2 points at the same location (" + e.Storeid + ").");
        } catch (ItemIsNotSoldAtAllException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! -Item #" + e.ItemID + "(" + e.ItemName + ") has no store that sell it");
        } catch (NegativePriceException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains a Negative Price " + e.PriceReceived + " for Item!");
        } catch (PointOutOfGridException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Location is not in at Range - " +e.PointReceived.toString());
        } catch (StoreDoesNotSellItemException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error - XML contains Store (" + e.StoreID + ") that does not sell any item");
        } catch (StoreItemNotInSystemException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Store #" + e.StoreIdInput + " is trying to sell an item that's not in system (Item #" + e.ItemIdInput + ")");
        } catch (WrongPayingMethodException e) {
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Wrong input Paying method - " + e.PayingInput);
        } catch (NoValidXMLException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML location was not found - please check path");
        } catch (NoOffersInDiscountException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Discount does not offer what you get");
        } catch (IllegalOfferException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Offer " + e.OfferName + " Is Illegal");
        } catch (NegativeQuantityException e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - Offer Quantity is Negative "+ e.Quantity);
        } catch (DuplicateCustomerInSystemException e) {
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Error! - XML contains 2 Customers with the same id : "+ e.id);
        } catch (Exception e) {
            MassageLabel.getStyleClass().clear();
            MassageLabel.getStyleClass().add("Error-Label");
            MassageLabel.setText("Unknown Error!");
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
