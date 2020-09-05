package course.java.sdm.gui.CustomersMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CustomersMenuTileController {

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label NumOfOrderLabel;

    @FXML
    private Label AvgShippingLabel;

    @FXML
    private Label AvgOrderLabel;

    public void SetValues (String ID,String Name,String Location,String NumOfOrder,String PriceShipping,String PriceOrders) {
        IDLabel.setText(ID);
        NameLabel.setText(Name);
        LocationLabel.setText(Location);
        NumOfOrderLabel.setText(NumOfOrder);
        AvgOrderLabel.setText(PriceOrders);
        AvgShippingLabel.setText(PriceShipping);
    }

}
