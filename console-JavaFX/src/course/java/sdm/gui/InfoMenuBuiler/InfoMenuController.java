package course.java.sdm.gui.InfoMenuBuiler;

import course.java.sdm.gui.CustomersMenu.CustomersMenuTileController;
import course.java.sdm.gui.ShowItemsMenu.ShowItemsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    public void AddNewItem (String ID,String Name,String payingMethodLabel,String sellingStoresLabel,String avgPriceLabel,String soldNumberLabel) throws Exception {

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = ShowItemsController.class.getResource("ShowItemsTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        ShowItemsController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.SetValues(ID,Name,payingMethodLabel,sellingStoresLabel,avgPriceLabel,soldNumberLabel);
        VBoxPane.getChildren().add(Tile);

    }

}
