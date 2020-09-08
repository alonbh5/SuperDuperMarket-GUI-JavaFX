package course.java.sdm.classesForUI;



import course.java.sdm.classesForUI.*;

import java.util.Date;

public class OrdersInStoreInfo {

    public final Long OrderSerialNumber;
    public final Date Date;
    public final Double TotalPrice;
    public final Double ShippingPrice;
    public final Double ItemsPrice;
    public final Integer amountOfItems;


    public OrdersInStoreInfo(Long orderSerialNumber, java.util.Date date, Double totalPrice, Double shippingPrice, Double itemsPrice, Integer amountOfItems) {
        OrderSerialNumber = orderSerialNumber;
        Date = date;
        TotalPrice = totalPrice;
        ShippingPrice = shippingPrice;
        ItemsPrice = itemsPrice;
        this.amountOfItems = amountOfItems;
    }
}
