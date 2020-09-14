package course.java.sdm.gui.InfoMenuBuiler;

import course.java.sdm.gui.CustomersMenu.CustomersMenuTileController;
import course.java.sdm.gui.OrderMenu.OrderItemTileController;
import course.java.sdm.gui.OrderMenu.OrderMenuTileController;
import course.java.sdm.gui.OrderMenu.OrderStoreTileController;
import course.java.sdm.gui.ShowItemsMenu.ShowItemsController;
import course.java.sdm.gui.StoresMenu.StoreItemTileController;
import course.java.sdm.gui.StoresMenu.StoresMenuTileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;

public class InfoMenuController {

    @FXML
    private VBox VBoxPane;


    public void AddNewCustomer (String ID,String Name,String Location,String NumOfOrder,String PriceShipping,String PriceOrders) throws Exception {

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = CustomersMenuTileController.class.getResource("CustomerMenuTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        CustomersMenuTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.SetValues(ID,Name,Location,NumOfOrder,PriceShipping,PriceOrders);
        VBoxPane.getChildren().add(Tile);

    }


    public void AddNewOrder (String ID, String Date, String UserName, String Location, String OrderType, ScrollPane items,ScrollPane Stores,
                             String PriceShipping,String PriceItems,String PriceTotal) throws Exception {

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = OrderMenuTileController.class.getResource("OrderMenuTile.fxml");
        fxmlLoader.setLocation(url);
        VBox Tile = fxmlLoader.load(url.openStream());
        OrderMenuTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.setValues(ID,Date,UserName,Location,OrderType,items,Stores,PriceShipping,PriceItems,PriceTotal);
        VBoxPane.getChildren().add(Tile);

    }

    public void AddNewStore(String Id, String Name, String Location, String PPK, String ShippingProfit, ScrollPane Discounts, ScrollPane Items, ScrollPane Orders) throws Exception {
        URL url = StoresMenuTileController.class.getResource("StoresMenuTile.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        VBox Tile = fxmlLoader.load(url.openStream());
        StoresMenuTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.setValues(Id,Name,Location,PPK,ShippingProfit,Discounts,Items,Orders);
        VBoxPane.getChildren().add(Tile);
    }


    public void AddNewStoreItem(String serialNumber, String name, String PayBy, String priceInStore, String soldCounter) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = StoreItemTileController.class.getResource("StoreItemTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        StoreItemTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.SetValues(serialNumber,name,PayBy,priceInStore,soldCounter);
        VBoxPane.getChildren().add(Tile);
    }

    public void AddNewStoreDiscount(String serialNumber, String name, String PayBy, String priceInStore, String soldCounter) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = StoreItemTileController.class.getResource("StoreItemTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        StoreItemTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.SetValues(serialNumber,name,PayBy,priceInStore,soldCounter);
        VBoxPane.getChildren().add(Tile);
    }

    public void AddNewStoreOrder(String serialNumber, String name, String PayBy, String priceInStore, String soldCounter) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = StoreItemTileController.class.getResource("StoreItemTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        StoreItemTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.SetValues(serialNumber,name,PayBy,priceInStore,soldCounter);
        VBoxPane.getChildren().add(Tile);
    }

    public void AddNewOrderItem(String ID,String Name,String PayBy,String PricePerUnit,String Amount,String TotalPrice,String StoreInfo,boolean isDiscount) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = OrderItemTileController.class.getResource("OrderItemTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        OrderItemTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.setValues(ID,Name,PayBy,PricePerUnit,Amount,TotalPrice,StoreInfo,isDiscount);
        VBoxPane.getChildren().add(Tile);
    }

    public void AddNewOrderStore(String ID, String Name, String PPK, String distanceFromUser, String shippingPriceFromUser) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = OrderStoreTileController.class.getResource("OrderStoreTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        OrderStoreTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.setValues(ID,Name,PPK,distanceFromUser,shippingPriceFromUser);
        VBoxPane.getChildren().add(Tile);
    }
}
