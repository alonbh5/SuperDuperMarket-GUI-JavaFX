package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.OrderInfo;
import course.java.sdm.gui.OrderMenu.OrderMenuTileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;


public class OrderSumUoController {

    @FXML    private Button DoneButton;

    @FXML    private Button CancelButton;

    @FXML    private ScrollPane MainScrollPane;

    @FXML    private BorderPane SetOnCenter;

    private Runnable finish;
    private Runnable Cancel;


    public void setOrder (OrderInfo order,Runnable OnFinish,Runnable OnCancel) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = OrderMenuTileController.class.getResource("OrderMenuTile.fxml");
        fxmlLoader.setLocation(url);
        VBox Tile = fxmlLoader.load(url.openStream());
        OrderMenuTileController TileController = fxmlLoader.getController();

        // add sub components to master app placeholders
        TileController.setValues(order);
        SetOnCenter.setCenter(Tile);
        finish = OnFinish;
        Cancel = OnCancel;
    }

    @FXML
    void OnDoneAction(ActionEvent event) {
         this.finish.run();
    }


    @FXML
    void OnCancelButton(ActionEvent event) {
        this.Cancel.run();
    }

}
