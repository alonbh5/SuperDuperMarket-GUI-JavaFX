package course.java.sdm.engine;
import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order implements Coordinatable{

    private static long serialGenerator = 5000000;

    private final Customer m_Customer;
    private final long m_serialNumber;
    private Date m_Date;
    private double m_TotalPrice=0;
    private double m_ShippingPrice=0;
    private double m_ItemsPrice=0;
    private int m_amountOfItems;
    private final Set<Item> m_Basket = new HashSet<Item>(); //todo what about prices?
    //todo need stores?

    public Order(Customer m_Customer, Date m_Date) {
        this.m_serialNumber = serialGenerator++;
        this.m_Customer = m_Customer;
        this.m_Date = m_Date;
    }

    public Order(Customer m_Customer, Date m_Date, double m_TotalPrice, double m_ShippingPrice, double m_ItemsPrice) {
        this.m_serialNumber = serialGenerator++;
        this.m_Customer = m_Customer;
        this.m_Date = m_Date;
        this.m_TotalPrice = m_TotalPrice;
        this.m_ShippingPrice = m_ShippingPrice;
        this.m_ItemsPrice = m_ItemsPrice;
    }

    @Override
    public Point getCoordinate() {
        return m_Customer.getCoordinate();
    }

    public Customer getCustomer() {
        return m_Customer;
    }

    public long getSerialNumber() {
        return m_serialNumber;
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

    public int getAmountOfItems() {
        return m_amountOfItems;
    }
}
