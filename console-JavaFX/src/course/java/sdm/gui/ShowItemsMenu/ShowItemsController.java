package course.java.sdm.gui.ShowItemsMenu;


import course.java.sdm.classesForUI.ItemInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;

public class ShowItemsController {

    @FXML
    private TableView<ItemInfo> ItemTable;

    @FXML
    private TableColumn<ItemInfo, Long> itemsIdColumn;

    @FXML
    private TableColumn<ItemInfo, String> ItemNameColumn;

    @FXML
    private TableColumn<ItemInfo, String> PayByColumn;

    @FXML
    private TableColumn<ItemInfo, Integer> StoresColumn;

    @FXML
    private TableColumn<ItemInfo, Double> AvgPriceColumn;

    @FXML
    private TableColumn<ItemInfo, Integer> SoldCounterColumn;

    @FXML
    private void initialize() {

        itemsIdColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayByColumn.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        StoresColumn.setCellValueFactory(new PropertyValueFactory<>("NumOfSellingStores"));
        AvgPriceColumn.setCellValueFactory(new PropertyValueFactory<>("AvgPrice"));
        SoldCounterColumn.setCellValueFactory(new PropertyValueFactory<>("SoldCount"));

    }

    public void setItems(Collection<ItemInfo> items) {
        ObservableList<ItemInfo> allItems = FXCollections.observableArrayList();
        allItems.addAll(items);
        ItemTable.setItems(allItems);
    }
}
