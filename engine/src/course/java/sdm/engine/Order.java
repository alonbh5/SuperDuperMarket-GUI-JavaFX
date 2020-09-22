package course.java.sdm.engine;
import course.java.sdm.classesForUI.ItemInOrderInfo;
import course.java.sdm.classesForUI.StoreInOrderInfo;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;


class Order implements Coordinatable, Serializable {

    private final long m_OrderSerialNumber;
    private Date m_Date;
    private final Customer m_Costumer; //todo change it to customer
    private double m_TotalPrice=0;
    private double m_ShippingPrice=0;
    private double m_ItemsPrice=0;
    private int m_amountOfItems=0;
    private final Set<ProductInOrder> m_Basket = new HashSet<>();
    private final Set<Store> m_StoresInOrder = new HashSet<>();
    private final boolean isStatic;

     Order(Customer Costumer, long m_OrderSerialNumber, Date m_Date,boolean isStatic) {
        this.m_Costumer = Costumer;
        this.m_OrderSerialNumber = m_OrderSerialNumber;
        this.m_Date = m_Date;
        this.isStatic=isStatic;
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

    Customer getCostumer () {return m_Costumer;};

    boolean isItemInBasket (ProductInOrder check)
    {
        return m_Basket.contains(check);
    }

    void addProductToOrder (ProductInOrder productToAdd)
    {
        if (!isStoreInOrder(productToAdd.getProductInStore().getStore()))
            m_ShippingPrice += SuperDuperMarketSystem.CalculatePPK(productToAdd.getProductInStore().getStore(),this.getCoordinate());
        m_Basket.add(productToAdd); //add order
        m_StoresInOrder.add(productToAdd.getProductInStore().getStore()); //add store to order stores list
        m_ItemsPrice += productToAdd.getPriceOfTotalItems();
        m_TotalPrice = m_ItemsPrice + m_ShippingPrice;
        m_amountOfItems += productToAdd.getAmountByPayingMethod();
    }

    @Override
    public String toString() { //q5
        return "Order#" + m_OrderSerialNumber + "at " + m_Date +
                "\n Number of Items: " + m_amountOfItems +
                "\n Cost of only Items: " + m_ItemsPrice +
                "\n Cost of Shipping: " + m_ShippingPrice +
                "\n Cost of Total Order: " + m_TotalPrice;
    }

    @Override
    public Point getCoordinate() {
        return m_Costumer.getCoordinate();
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
                m_Costumer.equals(order.m_Costumer) &&
                m_Basket.equals(order.m_Basket) &&
                m_StoresInOrder.equals(order.m_StoresInOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_OrderSerialNumber, m_Date, m_Costumer, m_TotalPrice, m_ShippingPrice, m_ItemsPrice, m_amountOfItems, m_Basket, m_StoresInOrder);
    }

    long getM_OrderSerialNumber() {
        return m_OrderSerialNumber;
    }

    Date getM_Date() {
        return m_Date;
    }

    double getM_TotalPrice() {
        return m_TotalPrice;
    }

    double getM_ShippingPrice() {
        return m_ShippingPrice;
    }

    double getM_ItemsPrice() {
        return m_ItemsPrice;
    }

    int getM_amountOfItems() {
        return m_amountOfItems;
    }

    Set<ProductInOrder> getM_Basket() {
        return m_Basket;
    }

    Set<Store> getM_StoresInOrder() {
        return m_StoresInOrder;
    }

    public long getCustomerID() {
         return m_Costumer.getId();
    }

    public Boolean isStatic() {
        return isStatic;
    }

    public List<ItemInOrderInfo> getItemsOnlyFromStore(long storeID) {
        List<ItemInOrderInfo> res = new ArrayList<>();

        for (ProductInOrder cur : m_Basket) {
            if (cur.getProductInStore().getStore().getStoreID() == storeID)
                res.add(new ItemInOrderInfo(cur.getSerialNumber(),cur.getProductInStore().getItem().getName(),
                        cur.getPayBy().toString(),cur.getProductInStore().getStore().getStoreID(),cur.getProductInStore().getStore().getName(),
                        cur.getAmount(),cur.getProductInStore().getPricePerUnit(),cur.getPriceOfTotalItems(),cur.isFromSale()));
        }
        return res;
    }

    public Integer getAmountOfItemFromStore(Store store) {
        Integer res= 0;
        for (ProductInOrder cur : m_Basket) {
            if(cur.getProductInStore().getStore().equals(store))
                res++;
        }
        return res;
    }

    public Double getPriceFromStore(Store store) {
        Double res= 0d;
        for (ProductInOrder cur : m_Basket) {
            if(cur.getProductInStore().getStore().equals(store))
                res+=cur.getPriceOfTotalItems();
        }
        return res;

    }

    public Long getStaticStore() {
        for (Store onlyStore : m_StoresInOrder) //works only if its static
            return onlyStore.getStoreID();
        return null;
    }

    public List<StoreInOrderInfo> getStoreInfo() {
        List<StoreInOrderInfo> storesList = new ArrayList<>();

        for (Store curStore : m_StoresInOrder)
            storesList.add(new StoreInOrderInfo(curStore.getStoreInfo(),
                    curStore.getCoordinate().distance(this.m_Costumer.getCoordinate()),
                    SuperDuperMarketSystem.CalculatePPK(curStore,this.m_Costumer.getCoordinate()),getPriceFromStore(curStore),
                    getAmountOfItemFromStore(curStore)));
        return storesList;
    }
}
