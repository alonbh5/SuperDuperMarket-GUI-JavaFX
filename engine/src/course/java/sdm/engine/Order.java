package course.java.sdm.engine;
import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order implements Coordinatable{

    private final Point m_userLocation;
    private final long m_serialNumber;
    private Date m_Date;
    private double m_TotalPrice=0;
    private double m_ShippingPrice=0;
    private double m_ItemsPrice=0;
    private int m_amountOfItems;
    private final Set<Item> m_Basket = new HashSet<>(); //todo what about prices?
    //todo need stores?

    Order(Long serialGenerator, Date i_Date,Point i_userLocation) {
        this.m_userLocation = i_userLocation;
        this.m_serialNumber = serialGenerator;
        this.m_Date = i_Date;
    }

    public Order(Long serialGenerator, Date i_Date, double i_TotalPrice, double i_ShippingPrice, double i_ItemsPrice,Point i_userLocation) {
        this.m_serialNumber = serialGenerator;
        this.m_Date = i_Date;
        this.m_userLocation = i_userLocation;
        this.m_TotalPrice = i_TotalPrice;
        this.m_ShippingPrice = i_ShippingPrice;
        this.m_ItemsPrice = i_ItemsPrice;
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

    @Override
    public Point getCoordinate() {
        return m_userLocation;
    }
}
