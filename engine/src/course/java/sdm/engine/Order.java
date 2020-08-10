package course.java.sdm.engine;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

    private Customer m_Customer;
    private Date m_Date;
    private double m_TotalPrice;
    private double m_ShippingPrice;
    private double m_ItemsPrice;
    Set<Product> m_Basket = new HashSet<Product>();

    public Order(Customer m_Customer, Date m_Date) {
        this.m_Customer = m_Customer;
        this.m_Date = m_Date;
    }

    public Order(Customer m_Customer, Date m_Date, double m_TotalPrice, double m_ShippingPrice, double m_ItemsPrice, Set<Product> m_Basket) {
        this.m_Customer = m_Customer;
        this.m_Date = m_Date;
        this.m_TotalPrice = m_TotalPrice;
        this.m_ShippingPrice = m_ShippingPrice;
        this.m_ItemsPrice = m_ItemsPrice;
        this.m_Basket = m_Basket;
    }
}
