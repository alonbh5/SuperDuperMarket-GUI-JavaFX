package course.java.sdm.gui.StoresMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

    public void setValues(String Id, String Name, String Location, String PPK, String ShippingProfit, Pane Discounts, Pane Items,Pane Orders){
        IDLabel.setText(Id);
        NameLabel.setText(Name);
        LocationLabel.setText(Location);
        PpkLabel.setText(PPK);
        ShippingProfitLabel.setText(ShippingProfit);
        DiscountPane.getChildren().add(Discounts);
        ItemsPane.getChildren().add(Items);
        OrderPane.getChildren().add(Orders);
    }

}
