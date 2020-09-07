package course.java.sdm.gui.StoresMenu;

import course.java.sdm.classesForUI.DiscountInfo;
import course.java.sdm.classesForUI.ItemInStoreInfo;
import course.java.sdm.classesForUI.StoreInfo;
import course.java.sdm.gui.InfoMenuBuiler.InfoMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class StoresMenuTileController {

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label PpkLabel;

    @FXML
    private Label ShippingProfitLabel;

    @FXML
    private AnchorPane DiscountPane;

    @FXML
    private AnchorPane ItemsPane;

    @FXML
    private AnchorPane OrderPane;

    @FXML
    private TitledPane ItemTile;

    @FXML
    private TitledPane OrderTile;

    @FXML
    private TitledPane DiscountTile;

    @FXML
    private void initialize() {
        ItemTile.setExpanded(false);
        OrderTile.setExpanded(false);
        DiscountTile.setExpanded(false);
    }

    public void setValues(String Id, String Name, String Location, String PPK, String ShippingProfit, ScrollPane Discounts, ScrollPane Items, ScrollPane Orders){
        IDLabel.setText(Id);
        NameLabel.setText(Name);
        LocationLabel.setText(Location);
        PpkLabel.setText(PPK);
        ShippingProfitLabel.setText(ShippingProfit);
        DiscountPane.getChildren().add(Discounts);
        ItemsPane.getChildren().add(Items);
        OrderPane.getChildren().add(Orders);
    }

    public static ScrollPane getItemsPane (StoreInfo store) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (ItemInStoreInfo cur : store.Items) {
            InfoController.AddNewStoreItem(cur.serialNumber.toString(),cur.Name,cur.PayBy.toLowerCase(),cur.PriceInStore.toString(),cur.SoldCounter.toString());
        }

        return infoComponent;
    }

    public static ScrollPane getDiscountPane (StoreInfo store) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (DiscountInfo cur : store.Discount) {
        }

        return infoComponent;
    }

}
