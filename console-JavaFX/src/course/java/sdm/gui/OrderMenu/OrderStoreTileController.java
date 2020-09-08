package course.java.sdm.gui.OrderMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderStoreTileController {

    @FXML    private Label IDLabel;

    @FXML    private Label NameLabel;

    @FXML    private Label PPKLabel;

    @FXML    private Label DistanceLabel;

    @FXML    private Label ShippingCostLabel;

    public void setValues(String ID,String Name,String PPK,String Dis,String Shipping) {
        IDLabel.setText(ID);
        NameLabel.setText(Name);
        PPKLabel.setText(PPK);
        DistanceLabel.setText(Dis);
        ShippingCostLabel.setText(Shipping);
    }

}
