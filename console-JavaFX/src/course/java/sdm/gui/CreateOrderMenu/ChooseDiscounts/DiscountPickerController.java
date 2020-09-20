package course.java.sdm.gui.CreateOrderMenu.ChooseDiscounts;

import course.java.sdm.classesForUI.DiscountInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class DiscountPickerController {


    @FXML    private TilePane MainTilePane;

    @FXML    private Button DoneButton;

    private Runnable OnFinish;

    public void OnCreation (Collection<DiscountInfo> discounts, Runnable onFinish) throws IOException {
        for (DiscountInfo cur :discounts) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = ChooseDiscountsController.class.getResource("ChooseDiscounts.fxml");
            fxmlLoader.setLocation(url);
            Parent component = fxmlLoader.load(url.openStream());
            ChooseDiscountsController controller = fxmlLoader.getController();
            controller.OnCreation(cur);
            MainTilePane.getChildren().add(component);

        }
        OnFinish=onFinish;
    }

    @FXML
    void OnDoneAction(ActionEvent event) {
        OnFinish.run();
    }

}
