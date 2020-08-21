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

     Order(Point m_userLocation, long m_OrderSerialNumber, Date m_Date) {
        this.m_userLocation = m_userLocation;
        this.m_OrderSerialNumber = m_OrderSerialNumber;
        this.m_Date = m_Date;
    }

     long getOrderSerialNumber() {
        return m_OrderSerialNumber;
    }

     Date getDate() {
        return m_Date;
    }

     double getTotalPrice() {
        return m_TotalPrice;
    }

     double getShippingPrice() {
        return m_ShippingPrice;
    }

     double getItemsPrice() {
        return m_ItemsPrice;
    }

     Set<Store> getStoreSet ()
    {
        return m_StoresInOrder;
    }

     int getAmountOfItems() {
        return m_amountOfItems;
    }

     boolean isStoreInOrder (Store store)
    {
        return m_StoresInOrder.contains(store);
    }

    Set<ProductInOrder> getBasket() {
        return m_Basket;
    }

    boolean isItemInBasket (ProductInOrder check)
    {
        return m_Basket.contains(check);
    }

    void addProductToOrder (ProductInOrder productToAdd)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return m_OrderSerialNumber == order.m_OrderSerialNumber &&
                Double.compare(order.m_TotalPrice, m_TotalPrice) == 0 &&
                Double.compare(order.m_ShippingPrice, m_ShippingPrice) == 0 &&
                Double.compare(order.m_ItemsPrice, m_ItemsPrice) == 0 &&
                m_amountOfItems == order.m_amountOfItems &&
                m_Date.equals(order.m_Date) &&
                m_userLocation.equals(order.m_userLocation) &&
                m_Basket.equals(order.m_Basket) &&
                m_StoresInOrder.equals(order.m_StoresInOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_OrderSerialNumber, m_Date, m_userLocation, m_TotalPrice, m_ShippingPrice, m_ItemsPrice, m_amountOfItems, m_Basket, m_StoresInOrder);
    }
}
