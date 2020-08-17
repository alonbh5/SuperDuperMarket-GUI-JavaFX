package course.java.sdm.engine;

import course.java.sdm.exceptions.*;
import javax.management.openmbean.*;
import java.awt.*;
import java.util.*;

public class Store implements HasName, Coordinatable{

    private final Point m_locationCoordinate;  //todo this need final?
    private final long m_StoreID;
    private double m_profitFromShipping = 0;
    private final Map<Long,ProductInStore> m_items = new HashMap<>();
    private final Map<Long,Order> m_OrderHistory = new HashMap<>();
    private String m_Name;
    private int PPK;

    //private Map<Long,Double> m_itemsPrices = new HashMap<>();
    //private Map<Long,Integer> m_ItemsSold = new HashMap<>(); //todo this needs to work
    //private List<> m_OrderHistory = new LinkedList<>();

    //TODO how to implement price for item


    Store(Long i_serialNumber,Point i_locationCoordinate,String m_Name, int i_PPK) {
        this.m_StoreID = i_serialNumber;
        this.m_locationCoordinate = i_locationCoordinate;
        this.PPK=i_PPK;
        this.m_Name = m_Name;
    }

    int getPPK() {
        return PPK;
    }

    long getStoreID() {
        return m_StoreID;
    }

     double getProfitFromShipping() {
        return m_profitFromShipping;
    }

     void addItemToStore (ProductInStore ProductToAdd)
    {
        Long itemKey = ProductToAdd.getItem().getSerialNumber();

        if (m_items.containsKey(itemKey))
            throw (new KeyAlreadyExistsException("The key for "+ProductToAdd.getItem().getName()+" #"+itemKey+"is Already in Store #"+this.m_StoreID));
        if (ProductToAdd.getPricePerUnit() <=0)
            throw (new NegativePrice(ProductToAdd.getPricePerUnit()));
        if (ProductToAdd.getStore().getStoreID() != this.getStoreID())
            throw (new IllegalArgumentException("the Product belongs to store #"+ProductToAdd.getStore().getStoreID()+"and does not match store #"+this.getStoreID()));

        m_items.put(itemKey,ProductToAdd);
        //todo make sure when called system update sellingStore list
    }

    double getPriceForItem (Long ItemID)
    {
        if (m_items.containsKey(ItemID))
            return (m_items.get(ItemID).getPricePerUnit());
        else
            throw (new InvalidKeyException("item #"+ItemID+" is not in Store"));
    }

    double getPriceForItem (ProductInStore ItemToCheck)
    {
        return getPriceForItem(ItemToCheck.getItem().serialNumber);
    }

    boolean isItemInStore (Item ItemToCheck)
    {
        return m_items.containsKey(ItemToCheck.getSerialNumber());
    }

    boolean isItemInStore (Long ItemID)
    {
        return m_items.containsKey(ItemID);
    }

    void addOrderToStoreHistory (Order NewOrder)
    {
        if (NewOrder.isStoreInOrder(this)) //todo add here update for amount bought from store (not only +1)
            m_OrderHistory.put(NewOrder.getOrderSerialNumber(), NewOrder);
        else
            throw (new IllegalArgumentException("Order #"+NewOrder.getOrderSerialNumber()+" does not buy from store #"+this.getStoreID()));
    }

    @Override
    public String getName() {
        return m_Name;
    }

    @Override
    public void setName(String Input) {
        m_Name = Input;
    }

    @Override
    public Point getCoordinate() {
        return this.m_locationCoordinate;
    }

    void newShippingFromStore (double AmountToAdd)
    {
        m_profitFromShipping += AmountToAdd;
    }

    @Override
    public String toString() { //2.3
        return "Store #" + m_StoreID +
                "\" " + m_Name +
                "\" \n Sold Items:\n" + ItemListToString() +
                "\n Order History is:\n" + OrderHistoryToString() +
                "\n PPK is: " + PPK +
                " So Far Profit from Shipping is :" + m_profitFromShipping +"\n";
    }

    private String ItemListToString ()
    {
        StringBuilder res = new StringBuilder();
        int i = 1;

        if (m_items.isEmpty())
            res.append("Store in Empty of items!");
        else {

            for (ProductInStore curItem : m_items.values()) {
                res.append(i++ +". "+curItem+"\n");
            }
        }

        return res.toString();
    }

    private String OrderHistoryToString()
    {
        StringBuilder res = new StringBuilder();
        int i = 1;

        if (m_OrderHistory.isEmpty())
            res.append("Store Has never made a sale!");

        for (Order curOrder : m_OrderHistory.values())
        {
            res.append(i++ +". Order#" + curOrder.getOrderSerialNumber() +
                    "\n Number of Items: " + curOrder.getAmountOfItems() +
                    "\n Cost of only Items: " + curOrder.getItemsPrice() +
                    "\n Cost of Shipping: " + curOrder.getShippingPrice() +
                    "\n Cost of Total Order: " + curOrder.getShippingPrice());
        }
        return res.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
         return (this.getStoreID() == store.getStoreID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_locationCoordinate, m_StoreID, m_profitFromShipping, m_items, m_OrderHistory, m_Name, PPK);
    }
}
