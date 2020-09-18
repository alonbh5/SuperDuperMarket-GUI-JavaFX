package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.StoreInOrderInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import course.java.sdm.classesForUI.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Collection;

public class StoreSumUpController {

    @FXML    private TableView<StoreInOrderInfo> MainTable;

    @FXML    private TableColumn<StoreInOrderInfo, Long> StoreIdColumn;

    @FXML    private TableColumn<StoreInOrderInfo, String> StoreNameColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Integer> PPKColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Double> DistanceColumn;

    @FXML    private TableColumn<StoreInOrderInfo, String> LocationColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Double> ShippingColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Integer> AmountColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Double> ItemPriceColumn;

    @FXML    private Button DiscountsButton;

    private Runnable OnFinish;


    @FXML
    private void initialize() {

        StoreIdColumn.setCellValueFactory(new PropertyValueFactory<>("StoreId"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        DistanceColumn.setCellValueFactory(new PropertyValueFactory<>("DistanceFromUser"));
        ShippingColumn.setCellValueFactory(new PropertyValueFactory<>("ShippingCost"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("AmountOfItems"));
        ItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("PriceOfItems"));
        StoreNameColumn.setCellValueFactory(new PropertyValueFactory<>("StoreName"));
        PPKColumn.setCellValueFactory(new PropertyValueFactory<>("PPK"));

    }

    public void setValues (OrderInfo order,Runnable run) {
        setValues(order.Stores,run);
    }

    public void setValues (Collection<StoreInOrderInfo> stores,Runnable run) {
        ObservableList<StoreInOrderInfo> obsStores = FXCollections.observableArrayList();
        obsStores.addAll(stores);
        MainTable.setItems(obsStores);
        OnFinish = run;
    }



    @FXML
    void onDiscountAction(ActionEvent event) {
        if (OnFinish != null)
            OnFinish.run();
    }

}


