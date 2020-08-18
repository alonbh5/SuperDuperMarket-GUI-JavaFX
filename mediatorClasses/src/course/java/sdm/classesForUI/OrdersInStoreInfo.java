package course.java.sdm.classesForUI;

import java.util.Date;

public class OrdersInStoreInfo {

    public final long OrderSerialNumber;
    public final Date Date;
    public final double TotalPrice;
    public final double ShippingPrice;
    public final double ItemsPrice;
    public final int amountOfItems;

    public OrdersInStoreInfo(long orderSerialNumber, java.util.Date date, double totalPrice, double shippingPrice, double itemsPrice, int amountOfItems) {
        OrderSerialNumber = orderSerialNumber;
        Date = date;
        TotalPrice = totalPrice;
        ShippingPrice = shippingPrice;
        ItemsPrice = itemsPrice;
        this.amountOfItems = amountOfItems;
    }
}
