package course.java.sdm.gui.MapMenu;

import course.java.sdm.classesForUI.CustomerInfo;
import course.java.sdm.classesForUI.StoreInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.*;

public class ShowMapController {


    @FXML   private GridPane MainGrid;

    private int MaxCol;
    private int MaxRow;
    private double  TileRow;
    private double  TileCol;


    public void OnCreation (int MaxRows, int MaxColumns) {
        // x = columns | | | |
        MaxCol = MaxColumns;
        MaxRow = MaxRows;
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
        MainGrid.setScaleShape(false);
        TileRow = MainGrid.getHeight() / MaxRows /2;
        TileCol = MainGrid.getHeight() / MaxCol /2;
    }

    public void AddItem (StoreInfo store) {
        Point Location = store.locationCoordinate;
        ChangePoint(Location);
        ImageView newStore =  new ImageView("https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/32x32/store.png");

        //newStore.setPreserveRatio(true);
        newStore.setFitHeight(TileCol);
        newStore.setFitWidth(TileRow);


        Tooltip StoreInfo = new Tooltip(getStoreTip(store));
        Tooltip.install(newStore,StoreInfo);
        MainGrid.add(newStore,Location.x,Location.y);
    }

    public void AddItem (CustomerInfo customer) {
        Point Location = customer.Location;
        ChangePoint(Location);
        ImageView newStore =  new ImageView("https://icon-icons.com/icons2/11/PNG/32/person_user_customer_man_male_man_boy_people_1687.png");

        //newStore.setPreserveRatio(true);
        newStore.setFitHeight(TileCol);
        newStore.setFitWidth(TileRow);



        Tooltip StoreInfo = new Tooltip(getCustomerTip(customer));
        Tooltip.install(newStore,StoreInfo);
        MainGrid.add(newStore,Location.x,Location.y);

    }

    private String getCustomerTip(CustomerInfo customer) {
        return customer.name;
    }

    private String getStoreTip(StoreInfo store) {
        return store.Name;
    }

    private void ChangePoint (Point pt) {
    }

}
