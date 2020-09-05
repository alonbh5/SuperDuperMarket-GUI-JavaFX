package course.java.sdm.gui.CustomersMenu;

import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;

public class CustomersMenuController {

    @FXML
    private VBox VBoxPane;

    public void AddNewCustomer (String ID,String Name,String Location,String NumOfOrder,String PriceShipping,String PriceOrders) throws Exception {

        // load header component and controller from fxml
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = CustomersMenuController.class.getResource("CustomerMenuTile.fxml");
        fxmlLoader.setLocation(url);
        HBox Tile = fxmlLoader.load(url.openStream());
        CustomersMenuTileController TileController = fxmlLoader.getController();

        /*// load master app and controller from fxml
        fxmlLoader = new FXMLLoader();
        url = getClass().getResource(APP_FXML_LIGHT_RESOURCE);
        fxmlLoader.setLocation(url);
        BorderPane root = fxmlLoader.load(url.openStream());
        AppController appController = fxmlLoader.getController();*/ //not here

        // add sub components to master app placeholders
        TileController.SetValues(ID,Name,Location,NumOfOrder,PriceShipping,PriceOrders);
        VBoxPane.getChildren().add(Tile);

        /*// connect between controllers
        appController.setBodyComponentController(bodyController);
        appController.setHeaderComponentController(headerController);

        Scene scene = new Scene(root, 500, 550);
        primaryStage.setScene(scene);
        primaryStage.show();*/

    }

}
