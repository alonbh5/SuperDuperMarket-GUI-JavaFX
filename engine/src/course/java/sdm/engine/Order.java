package course.java.sdm.engine;
import java.awt.*;
import java.util.*;

public class Order implements Coordinatable{

    private final long m_OrderSerialNumber;
    private final Point m_userLocation;
    private Date m_Date;
    private double m_TotalPrice=0;
    private double m_ShippingPrice=0;
    private double m_ItemsPrice=0;
    private int m_amountOfItems=0;
    private final Set<Product> m_Basket = new HashSet<>();
    private final Set<Long> m_StoreIDinOrder = new HashSet<>();

    public Order(Point m_userLocation, long m_OrderSerialNumber, Date m_Date) {
        this.m_userLocation = m_userLocation;
        this.m_OrderSerialNumber = m_OrderSerialNumber;
        this.m_Date = m_Date;
    }

    public long getOrderSerialNumber() {
        return m_OrderSerialNumber;
    }

    public Date getDate() {
        return m_Date;
    }

    public double getTotalPrice() {
        return m_TotalPrice;
    }

    public double getShippingPrice() {
        return m_ShippingPrice;
    }

    public double getItemsPrice() {
        return m_ItemsPrice;
    }

    public Set<Long> getStoreIDSet ()
    {
        return m_StoreIDinOrder;
    }

    public int getAmountOfItems() {
        return m_amountOfItems;
    }

    public void addProductToOrder (Product productToAdd,SuperDuperMarketSystem Sys)
    {
        m_Basket.add(productToAdd);
        m_StoreIDinOrder.add(productToAdd.getStoreID());
        m_ItemsPrice += productToAdd.getTotalPrice();
        m_ShippingPrice += Sys.CalculatePPK(productToAdd.getStoreID(),this.getCoordinate()); //todo check exc
        m_TotalPrice = m_ItemsPrice + m_ShippingPrice;
        if (productToAdd.getPayBy().equals(Item.payByMethod.AMOUNT))
            m_amountOfItems += ((int) productToAdd.getAmount());
        else
            m_amountOfItems++;
        //todo sys.itemhadbensold from system?,
    }

    @Override
    public String toString() {
        return "Order#" + m_OrderSerialNumber +
                "\n Number of Items: " + m_amountOfItems +
                "\n Cost of only Items: " + m_ItemsPrice +
                "\n Cost of Shipping: " + m_ShippingPrice +
                "\n Cost of Total Order: " + m_TotalPrice;
    }

    @Override
    public Point getCoordinate() {
        return m_userLocation;
    }
}
