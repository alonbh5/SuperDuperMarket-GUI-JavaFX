package course.java.sdm.gui.OrderMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderItemTileController {

    @FXML    private Label IDLabel;

    @FXML    private Label NameLabel;

    @FXML    private Label PayingMethodLabel;

    @FXML    private Label PricePerUnitLabel;

    @FXML    private Label AmountLabel;

    @FXML    private Label TotalPriceLabel;

    @FXML    private Label StoreIDLabel;

    @FXML    private Label DiscountLabel;


    public void setValues (String ID,String Name,String PayBy,String PricePerUnit,String Amount,String TotalPrice,String StoreInfo,boolean isDiscount) {
        IDLabel.setText(ID);
        NameLabel.setText(Name);
        PayingMethodLabel.setText(PayBy);
        PricePerUnitLabel.setText(PricePerUnit);
        AmountLabel.setText(Amount);
        TotalPriceLabel.setText(TotalPrice);
        StoreIDLabel.setText(StoreInfo);
        if (isDiscount)
            DiscountLabel.setText("From Discount");
        else
            DiscountLabel.setText("Not From Discount");
    }


}
