package course.java.sdm.gui.StoresMenu.SmallTiles;

import course.java.sdm.classesForUI.DiscountInfo;
import course.java.sdm.classesForUI.OfferItemInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class DiscountFromStoreTileController {

    @FXML
    private Label DiscountLabelName;

    @FXML
    private Label BuyLabel;

    @FXML
    private Label getLabel;

    @FXML
    private Button LeftButton;

    @FXML
    private Button RightButton;

    @FXML
    private AnchorPane MainAnchorPane;

    private DiscountInfo discount;

    private int i = 0;
    private List<String> msgArray = new ArrayList<>();

    public void SetValues(DiscountInfo discountTile) {
        discount = discountTile;

        DiscountLabelName.setText(discountTile.Name + " (" + discountTile.getDiscountOp() + ")");
        BuyLabel.setText("If You Buy " + discountTile.AmountToBuy.toString() + "\n" +
                discountTile.itemToBuy.Name + "'s (Item #" + discountTile.itemToBuy.ID + ")\nYou Can Get:");

        for (OfferItemInfo curGet : discountTile.OfferedItem) {
            i++;
            msgArray.add(new String(i + ") " + curGet.Amount + " " + curGet.Name + "'s (#" + curGet.ID + ")\n" +
                    "in the Price of " + curGet.PricePerOne + " for one"));
        }
        i = 0;

        getLabel.setText(msgArray.get(i));
    }


    @FXML
    void OnLeft(ActionEvent event) {
        i = Math.abs((i - 1) % msgArray.size());
        getLabel.setText(msgArray.get(i));

    }

    @FXML
    void OnRight(ActionEvent event) {
        i = Math.abs((i + 1) % msgArray.size());
        getLabel.setText(msgArray.get(i));

    }

    public void changeCssClass(boolean flag) {
        /*MainAnchorPane.getStyleClass().clear();
        if (flag)
            MainAnchorPane.getStyleClass().add("Block1");
        else
            MainAnchorPane.getStyleClass().add("Block2");*/
    }


}

