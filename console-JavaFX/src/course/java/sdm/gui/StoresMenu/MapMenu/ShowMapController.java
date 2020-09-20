package course.java.sdm.gui.StoresMenu.MapMenu;

import course.java.sdm.classesForUI.StoreInfo;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.awt.*;

public class ShowMapController {


    @FXML   private GridPane MainGrid;

    private int MaxCol;
    private int MaxRow;

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
            MainGrid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < MaxRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / MaxRows);
            MainGrid.getRowConstraints().add(rowConst);
        }

    }

    public void AddStore (StoreInfo store) {
        Point Location = store.locationCoordinate;
        ChangePoint(Location);
        ImageView newStore =  new ImageView("https://img.icons8.com/cotton/1x/online-store.png");

        newStore.setPreserveRatio(true);


        Tooltip StoreInfo = new Tooltip(getStoreTip(store));
        Tooltip.install(newStore,StoreInfo);
        MainGrid.add(newStore,Location.x,Location.y);
    }

    private String getStoreTip(StoreInfo store) {
        return store.Name;
    }

    private void ChangePoint (Point pt) {
    }

}
