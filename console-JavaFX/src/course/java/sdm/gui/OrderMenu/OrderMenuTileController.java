package course.java.sdm.gui.OrderMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class OrderMenuTileController {

    @FXML    private Label OrderIdLabel;

    @FXML    private Label DateLabel;

    @FXML    private Label UserNameLabel;

    @FXML    private Label NameLabel;

    @FXML    private Label LocationLabel;

    @FXML    private TitledPane ItemTile;

    @FXML    private AnchorPane ItemsPane;

    @FXML    private TitledPane StoreTile;

    @FXML    private AnchorPane DiscountPane;

    @FXML    private Label ItemPriceLabel;

    @FXML    private Label ShippingPriceLabel;

    @FXML    private Label TotalPriceLabel;

    @FXML
    private void initialize() {
        ItemTile.setExpanded(false);
        StoreTile.setExpanded(false);
    }

}
