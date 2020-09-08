package course.java.sdm.gui.OrderMenu;


import course.java.sdm.classesForUI.*;
import course.java.sdm.classesForUI.OrderInfo;
import course.java.sdm.gui.InfoMenuBuiler.InfoMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class OrderMenuTileController {

    @FXML    private Label OrderIdLabel;

    @FXML    private Label DateLabel;

    @FXML    private Label UserNameLabel;

    @FXML    private Label LocationLabel;

    @FXML    private Label OrderTypeLabel;

    @FXML    private TitledPane ItemTile;

    @FXML    private AnchorPane ItemsPane;

    @FXML    private TitledPane StoreTile;

    @FXML    private AnchorPane StoresPane;

    @FXML    private Label ItemPriceLabel;

    @FXML    private Label ShippingPriceLabel;

    @FXML    private Label TotalPriceLabel;



    @FXML
    private void initialize() {
        ItemTile.setExpanded(false);
        StoreTile.setExpanded(false);
    }

    public void setValues (String ID, String Date, String UserName, String Location, String OrderType, ScrollPane items,ScrollPane Stores,
                           String PriceShipping,String PriceItems,String PriceTotal) {
        OrderIdLabel.setText(ID);
        DateLabel.setText(Date);
        UserNameLabel.setText(UserName);
        LocationLabel.setText(Location);
        OrderTypeLabel.setText(OrderType);
        ItemPriceLabel.setText(PriceItems);
        ShippingPriceLabel.setText(PriceShipping);
        TotalPriceLabel.setText(PriceTotal);
        StoresPane.getChildren().add(Stores);
        ItemsPane.getChildren().add(items);
    }

    public static ScrollPane getItemsTile(OrderInfo order) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (ItemInOrderInfo cur : order.ItemsInOrder) {
            InfoController.AddNewOrderItem(cur.serialNumber.toString(),cur.Name,cur.PayBy.toLowerCase(),cur.PricePerUint.toString(),cur.amountBought.toString(),cur.TotalPrice.toString()
            ,cur.FromStoreID.toString(),cur.FromSale);
        }

        return infoComponent;
    }

    public static ScrollPane getStoresTile(OrderInfo order) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (StoreInfo cur : order.Stores) {
            InfoController.AddNewOrderStore(cur.StoreID.toString(),cur.Name,cur.PPK.toString(),cur.getDistanceFromUser(order.customer.Location),cur.getShippingPriceFromUser(order.customer.Location));
        }

        return infoComponent;
    }

}
