package course.java.sdm.gui.StoresMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StoreItemTileController {

    @FXML
    private Label IDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PayingMethodLabel;

    @FXML
    private Label PriceLabel;

    @FXML
    private Label AmountSoldLabel;

    public void SetValues (String ID,String Name,String payingMethodLabel,String sellingStores,String AmountSold) {
        IDLabel.setText(ID);
        NameLabel.setText(Name);
        PayingMethodLabel.setText(payingMethodLabel);
        PriceLabel.setText(sellingStores);
        AmountSoldLabel.setText(AmountSold);

    }

}
