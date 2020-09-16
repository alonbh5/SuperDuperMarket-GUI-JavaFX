package course.java.sdm.gui.StoresMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StoreOrderTileController {

    @FXML    private Label IDLabel;

    @FXML    private Label NameLabel;

    @FXML    private Label PayingMethodLabel;

    @FXML    private Label PriceLabel;

    @FXML    private Label AmountSoldLabel;

    @FXML    private Label isStaticLabel;


    public void changeToDynamic (){
        isStaticLabel.setText("Part Of Dynamic Order");
    }

}
