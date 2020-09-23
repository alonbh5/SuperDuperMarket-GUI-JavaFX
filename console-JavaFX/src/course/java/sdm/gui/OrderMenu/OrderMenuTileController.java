package course.java.sdm.gui.OrderMenu;


import course.java.sdm.classesForUI.*;
import course.java.sdm.classesForUI.OrderInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


public class OrderMenuTileController {

    @FXML    private Label OrderIdLabel;

    @FXML    private Label DateLabel;

    @FXML    private Label UserNameLabel;

    @FXML    private Label LocationLabel;

    @FXML    private Label OrderTypeLabel;

    @FXML    private TitledPane ItemTile;

    @FXML    private AnchorPane ItemsPane;

    @FXML    private TableView<ItemInOrderInfo> ItemTable;

    @FXML    private TableView<StoreInOrderInfo> StoreTable;

    @FXML    private TableColumn<ItemInOrderInfo, Long> ItemIdColumn;

    @FXML    private TableColumn<ItemInOrderInfo, String> ItemNameColumn;

    @FXML    private TableColumn<ItemInOrderInfo, String> ItemPayByColumn;

    @FXML    private TableColumn<ItemInOrderInfo, Double> ItemAmountColumn;

    @FXML    private TableColumn<ItemInOrderInfo, Double> ItemPriceColumn;

    @FXML    private TableColumn<ItemInOrderInfo, Double> ItemTotalColumn;

    @FXML    private TableColumn<ItemInOrderInfo, String> ItemDisColumn;

    @FXML    private TableColumn<ItemInOrderInfo, String> ItemStoreColumn;

    @FXML    private TitledPane StoreTile;

    @FXML    private AnchorPane StoresPane;

    @FXML    private TableColumn<StoreInOrderInfo, Long> StoreIdColumn;

    @FXML    private TableColumn<StoreInOrderInfo, String> StoreNameColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Integer> PPKColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Double> DistanceColumn;

    @FXML    private TableColumn<StoreInOrderInfo, Double> ShippingColumn;

    @FXML    private Label ItemPriceLabel;

    @FXML    private Label ShippingPriceLabel;

    @FXML    private Label TotalPriceLabel;



    @FXML
    private void initialize() {
        ItemTile.setExpanded(false);
        StoreTile.setExpanded(false);

        ItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ItemPayByColumn.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        ItemAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amountBought"));
        ItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("PricePerUint"));
        ItemTotalColumn.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        ItemDisColumn.setCellValueFactory(new PropertyValueFactory<>("IsDiscount"));
        ItemStoreColumn.setCellValueFactory(new PropertyValueFactory<>("FromStore"));

        StoreIdColumn.setCellValueFactory(new PropertyValueFactory<>("StoreId"));
        StoreNameColumn.setCellValueFactory(new PropertyValueFactory<>("StoreName"));
        PPKColumn.setCellValueFactory(new PropertyValueFactory<>("PPK"));
        DistanceColumn.setCellValueFactory(new PropertyValueFactory<>("DistanceFromUser"));
        ShippingColumn.setCellValueFactory(new PropertyValueFactory<>("ShippingCost"));
    }

    public void setValues (OrderInfo order) {
        OrderIdLabel.setText(order.m_OrderSerialNumber.toString());
        DateLabel.setText(order.getDateString());
        UserNameLabel.setText(order.customer.name + " (#"+order.customer.ID+")");
        LocationLabel.setText(order.getPointString());
        if (order.isStatic)
            OrderTypeLabel.setText("Static");
        else
            OrderTypeLabel.setText("Dynamic");
        ItemPriceLabel.setText(String.format("%.2f", order.m_ItemsPrice));
        ShippingPriceLabel.setText(String.format("%.2f", order.m_ShippingPrice));
        TotalPriceLabel.setText(String.format("%.2f", order.m_TotalPrice));

        ObservableList<ItemInOrderInfo> items = FXCollections.observableArrayList();
        ObservableList<StoreInOrderInfo> stores = FXCollections.observableArrayList();
        items.addAll(order.ItemsInOrder);
        stores.addAll(order.Stores);
        ItemTable.setItems(items);
        StoreTable.setItems(stores);
    }





}
