package course.java.sdm.classesForUI;

import java.awt.*;
import java.util.List;


public class StoreInfo {
    public final Point locationCoordinate;
    public final long StoreID;
    public final double profitFromShipping;
    public final List<ItemInStoreInfo> Items;
    public final List<OrdersInStoreInfo> OrderHistory ; //todo add here discount.....
    public final String Name;
    public final int PPK;

    public StoreInfo(Point locationCoordinate, long storeID, String name, int PPK,List<ItemInStoreInfo> itemsList,List<OrdersInStoreInfo> orders,double profit) {
        this.locationCoordinate = locationCoordinate;
        StoreID = storeID;
        Name = name;
        this.PPK = PPK;
        this.Items = itemsList;
        this.OrderHistory=orders;
        this.profitFromShipping = profit;
    }

    public boolean isItemIDinStore (long ItemID)
    {
        return Items.stream().anyMatch(t->t.serialNumber == ItemID);
    }
}
