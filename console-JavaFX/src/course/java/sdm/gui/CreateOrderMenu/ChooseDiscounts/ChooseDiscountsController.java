package course.java.sdm.gui.CreateOrderMenu.ChooseDiscounts;

import course.java.sdm.classesForUI.DiscountInfo;
import course.java.sdm.classesForUI.OfferItemInfo;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.xml.ws.Binding;
import java.util.ArrayList;
import java.util.List;

public class ChooseDiscountsController {

    @FXML    private AnchorPane MainAnchorPane;

    @FXML    private Label DiscountLabelName;

    @FXML    private Label BuyLabel;

    @FXML    private Label getLabel;

    @FXML    private Button LeftButton;

    @FXML    private Button RightButton;

    @FXML    private Label EntltiledLabel;

    @FXML    private Label WantedLabel;

    @FXML    private Button PickButton;

    private DiscountInfo discount;
    private int i = 0;
    private List<String> msgArray = new ArrayList<>();

    void OnCreation (DiscountInfo discountTile) {

        EntltiledLabel.textProperty().bind(discountTile.AmountEntitled.asString());
        WantedLabel.textProperty().bind(discountTile.AmountWanted.asString());
        PickButton.disableProperty().bind(Bindings.when(EntltiledLabel.textProperty().isEqualTo("0")).then(true).otherwise(false));
        discount = discountTile;

        DiscountLabelName.setText(discountTile.Name + "(" + discountTile.DiscountOperator + ")");
        BuyLabel.setText("If You Buy " + discountTile.AmountToBuy.toString() + "\n" +
                discountTile.itemToBuy.Name + "'s (Item #" + discountTile.itemToBuy.ID + ")\nYou Can Get:");

        for (OfferItemInfo curGet : discountTile.OfferedItem) {
            i++;
            msgArray.add(new String(i + ". " + curGet.Amount + " " + curGet.Name + "'s (#" + curGet.ID + ")\n" +
                    "in the Price of " + curGet.PricePerOne + " for one"));
        }
        i = 0;

        getLabel.setText(msgArray.get(i));
    }

    @FXML
    void OnDiscountPicked(ActionEvent event) {
        if (discount.isIndex())
            discount.setIndexOfWantedItem(i);
        discount.addAmountWanted();
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

    void changeCssClass(boolean flag) {
        MainAnchorPane.getStyleClass().clear();
        if (flag)
            MainAnchorPane.getStyleClass().add("Block1");
        else
            MainAnchorPane.getStyleClass().add("Block2");
    }

}
