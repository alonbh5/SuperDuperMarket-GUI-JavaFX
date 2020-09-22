package course.java.sdm.gui.MapMenu;

import course.java.sdm.classesForUI.CustomerInfo;
import course.java.sdm.classesForUI.StoreInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;

public class ShowMapController {


    @FXML   private GridPane MainGrid;

    private int MaxCol;
    private int MaxRow;
    private double  TileRow;
    private double  TileCol;


    public void OnCreation (int MaxRows, int MaxColumns) {
        // x = columns | | | |
        MaxCol = MaxColumns; //x
        MaxRow = MaxRows; //y

        MaxRows++;
        MaxColumns++;
        MainGrid.getColumnConstraints().clear();
        MainGrid.getRowConstraints().clear();

        for (int i = 0; i < MaxColumns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / MaxColumns);
           //colConst.setHgrow(Priority.NEVER);
            MainGrid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < MaxRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / MaxRows);
           // rowConst.setVgrow(Priority.NEVER);
            MainGrid.getRowConstraints().add(rowConst);
        }

       // MainGrid.setMaxSize(MainGrid.getWidth(),MainGrid.getHeight());
       // MainGrid.setScaleShape(false);
        TileRow = MainGrid.getHeight() / MaxRows;
        TileCol = MainGrid.getWidth() / MaxColumns ;

    }

    public void AddItem (StoreInfo store) {
        Point Location = new Point(store.locationCoordinate);
        ChangePoint(Location);
        ImageView newStore =  new ImageView("https://icon-icons.com/icons2/2063/PNG/32/store_shop_building_ecommerce_icon_124608.png");

        //newStore.setPreserveRatio(true);
        //newStore.setFitHeight(TileCol);
       // newStore.setFitWidth(TileRow);

        //StackPane newPane = new StackPane(newStore);
        StackPane newPane = new StackPane();
        newPane.getStyleClass().add("StoreStack");



        Tooltip StoreInfo = new Tooltip(getStoreTip(store));
        //Tooltip.install(newStore,StoreInfo);
        //MainGrid.add(newStore,Location.x,Location.y);
        Tooltip.install(newPane,StoreInfo);
        MainGrid.add(newPane,Location.x,Location.y);
    }

    public void AddItem (CustomerInfo customer) {
        Point Location = new Point(customer.Location);
        ChangePoint(Location);
        ImageView newCustomer =  new ImageView("https://icon-icons.com/icons2/11/PNG/32/person_user_customer_man_male_man_boy_people_1687.png");

        //newCustomer.setPreserveRatio(true);
        //newCustomer.setFitHeight(TileCol);
        //newCustomer.setFitWidth(TileRow);

        //StackPane newPane = new StackPane(newCustomer);
        StackPane newPane = new StackPane();
        newPane.getStyleClass().add("CustomerStack");

        Tooltip storeInfo = new Tooltip(getCustomerTip(customer));

        //Tooltip.install(newStore,StoreInfo);
        //MainGrid.add(newStore,Location.x,Location.y);
        Tooltip.install(newPane,storeInfo);
        MainGrid.add(newPane,Location.x,Location.y);
    }

    private String getCustomerTip(CustomerInfo customer) {
        return String.format("Location %s\n" +
                        "customer #%d - '%s'\n" +
                        "He Order %d times."
                ,customer.getLocationString(),customer.ID,customer.name,customer.AmountOfOrders);
    }

    private String getStoreTip(StoreInfo store) {
        return String.format("Location %s\n" +
                "Store #%d - '%s'\n" +
                "PPK is %d and Number of Orders is %d."
                ,store.getPointString(),store.StoreID,store.Name,store.PPK,store.OrderHistory.size());
    }

    private void ChangePoint (Point pt) { //todo
         pt.x--;
         pt.y = MaxRow - pt.y + 1;

    }

    /*if (pt.x == 1 && pt.y == 1)
        {
            pt.x=0;
            pt.y=35;
        }*/
}
