package course.java.sdm.engine;
import java.awt.*;
import java.util.*;

public class Order implements Coordinatable{

    private final long m_OrderSerialNumber;
    private Date m_Date;
    private final Point m_userLocation;
    private double m_TotalPrice=0;
    private double m_ShippingPrice=0;
    private double m_ItemsPrice=0;
    private int m_amountOfItems=0;
    private final Set<ProductInOrder> m_Basket = new HashSet<>();
    private final Set<Store> m_StoresInOrder = new HashSet<>();

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

    public Set<Store> getStoreSet ()
    {
        return m_StoresInOrder;
    }

    public int getAmountOfItems() {
        return m_amountOfItems;
    }

    public boolean isStoreInOrder (Store store)
    {
        return m_StoresInOrder.contains(store);
    }

    public void addProductToOrder (ProductInOrder productToAdd)
    {
        m_Basket.add(productToAdd); //add order
        m_StoresInOrder.add(productToAdd.getProductInStore().getStore()); //add store to order stores list
        m_ItemsPrice += productToAdd.getPriceOfTotalItems();
        m_ShippingPrice += SuperDuperMarketSystem.CalculatePPK(productToAdd.getProductInStore().getStore(),this.getCoordinate()); //todo check exc
        m_TotalPrice = m_ItemsPrice + m_ShippingPrice;
        m_amountOfItems += productToAdd.getAmountByPayingMethod();
        //Sys.OnItemHadBeenSold(productToAdd.getSerialNumber(),addedAmount);
        // todo make sure this works - updating amount sold SYstem need to take care of that!!!!
    }

    @Override
    public String toString() { //q5
        return "Order#" + m_OrderSerialNumber + "at " + m_Date + //todo need to add here store but stoers with bonus!!!
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
