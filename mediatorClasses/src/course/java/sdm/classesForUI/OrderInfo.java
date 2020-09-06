package course.java.sdm.classesForUI;


import java.util.Date;
import java.util.List;


public class OrderInfo {

    public final Long m_OrderSerialNumber;
    public final Date m_Date;
    public final List<String> Stores;
    public final List<ItemInOrderInfo> ItemsInOrder;
    public final CustomerInfo customer;
    public final Double m_TotalPrice;
    public final Double m_ShippingPrice;
    public final Double m_ItemsPrice;
    public final Integer m_amountOfItems;
    public final Double Distance;
    public final Integer StaticPPK;

    public OrderInfo(long m_OrderSerialNumber, Date m_Date, List<String> stores, List<ItemInOrderInfo> itemsInOrder, double m_TotalPrice, double m_ShippingPrice, double m_ItemsPrice, int m_amountOfItems,double distance,int PPK, CustomerInfo customer) {
        this.m_OrderSerialNumber = m_OrderSerialNumber;
        this.m_Date = m_Date;
        Stores = stores;
        ItemsInOrder = itemsInOrder;
        this.m_TotalPrice = m_TotalPrice;
        this.m_ShippingPrice = m_ShippingPrice;
        this.m_ItemsPrice = m_ItemsPrice;
        this.m_amountOfItems = m_amountOfItems;
        this.Distance = distance;
        this.StaticPPK=PPK;
        this.customer =   customer;
    }
}
