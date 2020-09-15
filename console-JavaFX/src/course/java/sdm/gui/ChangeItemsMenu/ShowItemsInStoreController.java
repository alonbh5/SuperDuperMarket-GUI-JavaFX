package course.java.sdm.gui.ChangeItemsMenu;

import course.java.sdm.classesForUI.ItemInStoreInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowItemsInStoreController {

    @FXML    private TableView<ItemInStoreInfo> ItemTable;

    @FXML    private TableColumn<ItemInStoreInfo, Long> ItemID;

    @FXML    private TableColumn<ItemInStoreInfo, String> ItemName;

    @FXML    private TableColumn<ItemInStoreInfo, String> PayBy;

    @FXML    private TableColumn<ItemInStoreInfo, Double> Price;

    private ChangeItemMenuController returnMenu;


    @FXML
    private void initialize() {

        ItemID.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayBy.setCellValueFactory(new PropertyValueFactory<>("PayBy"));
        Price.setCellValueFactory(new PropertyValueFactory<>("PriceInStore"));
    }

    public void setItems(List<ItemInStoreInfo> items,ChangeItemMenuController returnMenu) {
        this.returnMenu = returnMenu;
        ObservableList<ItemInStoreInfo> allItems = FXCollections.observableArrayList();
        allItems.addAll(items);
        ItemTable.setItems(allItems);

        ItemTable.setRowFactory(tv -> {
            TableRow<ItemInStoreInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ItemInStoreInfo rowData = row.getItem();
                    this.returnMenu.onPickedItem(rowData);
                }
            });
            return row ;
        });
    }

}
