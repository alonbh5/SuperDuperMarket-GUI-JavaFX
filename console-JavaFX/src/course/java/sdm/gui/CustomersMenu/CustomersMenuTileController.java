package course.java.sdm.gui.CustomersMenu;

import course.java.sdm.classesForUI.CustomerInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;

public class CustomersMenuTileController {

    @FXML
    private TableView<CustomerInfo> ItemTable;

    @FXML
    private TableColumn<CustomerInfo, Long> IdColumn;

    @FXML
    private TableColumn<CustomerInfo, String> NameColumn;

    @FXML
    private TableColumn<CustomerInfo, String> LocationColumn;

    @FXML
    private TableColumn<CustomerInfo, Integer> OrderCounterColumn;

    @FXML
    private TableColumn<CustomerInfo, Double> AvgShippingColumn;

    @FXML
    private TableColumn<CustomerInfo, Double> AvgOrderColumn;

    @FXML
    private void initialize() {

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        OrderCounterColumn.setCellValueFactory(new PropertyValueFactory<>("AmountOfOrders"));
        AvgShippingColumn.setCellValueFactory(new PropertyValueFactory<>("AvgPriceForShipping"));
        AvgOrderColumn.setCellValueFactory(new PropertyValueFactory<>("AvgPriceForOrderWithoutShipping"));
    }


    public void setValues(Collection<CustomerInfo> customers) {
        ObservableList<CustomerInfo> allItems = FXCollections.observableArrayList();
        allItems.addAll(customers);
        ItemTable.setItems(allItems);
    }
}
