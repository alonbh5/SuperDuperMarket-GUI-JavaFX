package course.java.sdm.gui.StoresMenu;

import course.java.sdm.classesForUI.*;
import course.java.sdm.gui.InfoMenuBuiler.InfoMenuController;
import course.java.sdm.gui.StoresMenu.SmallTiles.DiscountFromStoreTileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class StoresMenuTileController {

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML    private Label LocationLabel;

    @FXML    private Label PpkLabel;

    @FXML    private Label ShippingProfitLabel;

    @FXML    private AnchorPane DiscountPane;

    @FXML    private AnchorPane ItemsPane;

    @FXML    private AnchorPane OrderPane;

    @FXML    private TitledPane ItemTile;

    @FXML    private TitledPane OrderTile;

    @FXML    private TitledPane DiscountTile;

    @FXML    private FlowPane DiscountFlowPane;

    @FXML
    private void initialize() {
        ItemTile.setExpanded(false);
        OrderTile.setExpanded(false);
        DiscountTile.setExpanded(false);
    }

    public void setValues(String Id, String Name, String Location, String PPK, String ShippingProfit, Collection<DiscountInfo> discounts, ScrollPane Items, ScrollPane Orders) {
        IDLabel.setText(Id);
        NameLabel.setText(Name);
        LocationLabel.setText(Location);
        PpkLabel.setText(PPK);
        ShippingProfitLabel.setText(ShippingProfit);
        ItemsPane.getChildren().add(Items);
        if (discounts.isEmpty() || discounts == null) {
            DiscountTile.setText(DiscountTile.getText() + " (Empty)");
            DiscountTile.setCollapsible(false);
        }
        else
            setDiscount(discounts);

        if (Orders == null) {
            OrderTile.setText(OrderTile.getText() + " (Empty)");
            OrderTile.setCollapsible(false);
        }
        else
            OrderPane.getChildren().add(Orders);
    }

    private void setDiscount(Collection<DiscountInfo> discounts) {
        boolean flag = true;
        for (DiscountInfo cur:discounts) {
            flag = !flag;
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = DiscountFromStoreTileController.class.getResource("DiscountFromStoreTile.fxml"); //todo make it all in common static..
            fxmlLoader.setLocation(url);
            Parent infoComponent;
            try {
                Parent Component = fxmlLoader.load(url.openStream());
                DiscountFromStoreTileController DiscountController = fxmlLoader.getController();
                DiscountController.SetValues(cur);
                DiscountController.changeCssClass(flag);
                DiscountFlowPane.getChildren().add(Component);
            } catch (IOException e) {
                DiscountTile.setText(DiscountTile.getText()+" (Error)");
            }

        }
    }

    public static ScrollPane getItemsPane(StoreInfo store) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = InfoMenuController.class.getResource("InfoMenu.fxml"); //todo make it all in common static..
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (ItemInStoreInfo cur : store.Items) {
            InfoController.AddNewStoreItem(cur.serialNumber.toString(), cur.Name, cur.PayBy.toLowerCase(), cur.PriceInStore.toString(), cur.SoldCounter.toString());
        }

        return infoComponent;
    }

    public static ScrollPane getOrdersPane(StoreInfo store) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (store.OrderHistory.isEmpty()) {
            return null;
        }

        URL url = InfoMenuController.class.getResource("InfoMenu.fxml");
        fxmlLoader.setLocation(url);
        ScrollPane infoComponent = fxmlLoader.load(url.openStream());
        InfoMenuController InfoController = fxmlLoader.getController();

        for (OrderInfo cur : store.OrderHistory) {
            //if(!cur.isStatic) {
             //   cur.makeDynamic(store);
           // }
            InfoController.AddNewOrder(cur);
        }
        return infoComponent;
    }
}


