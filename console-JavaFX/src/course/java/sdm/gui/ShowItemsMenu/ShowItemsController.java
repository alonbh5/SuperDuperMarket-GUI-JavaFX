package course.java.sdm.gui.ShowItemsMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowItemsController {

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PayingMethodLabel;

    @FXML
    private Label SellingStoresLabel;

    @FXML
    private Label AvgPriceLabel;

    @FXML
    private Label SoldNumberLabel;


    public void SetValues (String ID,String Name,String payingMethodLabel,String sellingStoresLabel,String avgPriceLabel,String soldNumberLabel) {
        IDLabel.setText(ID);
        NameLabel.setText(Name);
        PayingMethodLabel.setText(payingMethodLabel);
        SellingStoresLabel.setText(sellingStoresLabel);
        AvgPriceLabel.setText(avgPriceLabel);
        SoldNumberLabel.setText(soldNumberLabel);
    }

}
