package course.java.sdm.classesForUI;

import java.awt.*;
import java.util.List;


public class StoreInfo {
    public final Point locationCoordinate;
    public final Long StoreID;
    public final Double profitFromShipping;
    public final List<ItemInStoreInfo> Items;
    public final List<OrderInfo> OrderHistory ;
    public final List<DiscountInfo> Discount;
    public final String Name;
    public final Integer PPK;

    public StoreInfo(Point locationCoordinate, Long storeID, Double profitFromShipping, List<ItemInStoreInfo> items, List<OrderInfo> orderHistory, List<DiscountInfo> discount, String name, Integer PPK) {
        this.locationCoordinate = locationCoordinate;
        StoreID = storeID;
        this.profitFromShipping = profitFromShipping;
        Items = items;
        OrderHistory = orderHistory;
        Discount = discount;
        Name = name;
        this.PPK = PPK;
    }

    public boolean isItemIDinStore (long ItemID)
    {
        return Items.stream().anyMatch(t->t.serialNumber == ItemID);
    }

    public String getPointString () {
        return ("("+(int)locationCoordinate.getX()+","+(int)locationCoordinate.getY()+")");
    }

    public String getDistanceFromUser (Point UserLocation) {
        return (Double.toString(locationCoordinate.distance(UserLocation)));
    }

    public String getShippingPriceFromUser (Point UserLocation) {
        return (Double.toString(PPK * locationCoordinate.distance(UserLocation)));
    }

    @Override
    public String toString() {
        return "Store #" + StoreID +
                " (" + Name +
                "), at " + getPointString() +
                ", PPK is" + PPK ;
    }
}
