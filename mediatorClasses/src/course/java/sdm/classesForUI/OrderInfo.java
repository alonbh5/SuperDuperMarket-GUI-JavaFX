package course.java.sdm.classesForUI;


import java.util.Date;
import java.util.List;


public class OrderInfo {

    public final long m_OrderSerialNumber;
    public final Date m_Date;
    public final List<String> Stores;
    public final List<ItemInOrderInfo> ItemsInOrder;
    public final double m_TotalPrice;
    public final double m_ShippingPrice;
    public final double m_ItemsPrice;
    public final int m_amountOfItems;
    public final double Distance;
    public final int StaticPPK;

    public OrderInfo(long m_OrderSerialNumber, Date m_Date, List<String> stores, List<ItemInOrderInfo> itemsInOrder, double m_TotalPrice, double m_ShippingPrice, double m_ItemsPrice, int m_amountOfItems,double distance,int PPK) {
        this.m_OrderSerialNumber = m_OrderSerialNumber;
        this.m_Date = m_Date;
        Stores = stores;
        ItemsInOrder = itemsInOrder;
        this.m_TotalPrice = m_TotalPrice;
        this.m_ShippingPrice = m_ShippingPrice;
        this.m_ItemsPrice = m_ItemsPrice;
        this.m_amountOfItems = m_amountOfItems;
        Distance = distance;
        StaticPPK=PPK;
    }
}
