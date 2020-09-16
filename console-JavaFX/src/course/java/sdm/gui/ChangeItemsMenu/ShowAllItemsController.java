package course.java.sdm.gui.ChangeItemsMenu;
import course.java.sdm.classesForUI.ItemInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ShowAllItemsController {
    @FXML
    private TableView<ItemInfo> ItemTable;

    @FXML    private TableColumn<ItemInfo, Long> ItemID;

    @FXML    private TableColumn<ItemInfo, String> ItemName;

    @FXML    private TableColumn<ItemInfo, String> PayBy;

    private ChangeItemMenuController returnMenu;


    @FXML
    private void initialize() {

        ItemID.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ItemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PayBy.setCellValueFactory(new PropertyValueFactory<>("PayBy"));

    }

    public void setItems(List<ItemInfo> items, ChangeItemMenuController returnMenu) {
        this.returnMenu = returnMenu;
        ObservableList<ItemInfo> allItems = FXCollections.observableArrayList();
        allItems.addAll(items);
        ItemTable.setItems(allItems);

        ItemTable.setRowFactory(tv -> {
            TableRow<ItemInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ItemInfo rowData = row.getItem();
                    this.returnMenu.onAddItemItem(rowData);
                }
            });
            return row ;
        });
    }

}

