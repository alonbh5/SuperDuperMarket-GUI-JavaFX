package course.java.sdm.engine;

import course.java.sdm.exceptions.*;
import javax.management.openmbean.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Store implements HasName, Coordinatable{

    private final Point m_locationCoordinate;  //todo this need final?
    private final long m_StoreID;
    private double m_profitFromShipping = 0;
    private Map<Long,Item> m_items = new HashMap<>();
    private Map<Long,Double> m_itemsPrices = new HashMap<>();
    private Map<Long,Integer> m_ItemsSold = new HashMap<>(); //todo this needs to wrok
    private List<Order> m_OrderHistory = new LinkedList<>();
    private String m_Name;
    private int PPK;

    //TODO how to implemnt price for item


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

    public double getProfitFromShipping() {
        return m_profitFromShipping;
    }

    public boolean addItemToStore (Item itemToAdd, double Price)
    {
        Long itemKey = itemToAdd.getSerialNumber();

        if (m_items.containsKey(itemKey))
            throw (new KeyAlreadyExistsException("The key for "+itemToAdd.getName()+" #"+itemKey+"is Already in Store #"+this.m_StoreID));
        if (Price <=0)
            throw (new NegativePrice(Price));

        m_items.put(itemKey,itemToAdd);
        m_itemsPrices.put(itemKey,Price);
        return true;
    }

    public double getPriceForItem (Long ItemID)
    {
        if (m_itemsPrices.containsKey(ItemID))
            return (m_itemsPrices.get(ItemID));
        else
            throw (new InvalidKeyException("item #"+ItemID+" is not in Store"));
    }

    public double getPriceForItem (Item ItemToCheck)
    {
        if (m_itemsPrices.containsKey(ItemToCheck.getSerialNumber()))
            return (m_itemsPrices.get(ItemToCheck.getSerialNumber()));
        else
            throw (new InvalidKeyException(ItemToCheck.getName()+"#"+ItemToCheck.getSerialNumber()+" is not in Store"));
    }

    public boolean isItemInStore (Item ItemToCheck)
    {
        return m_items.containsKey(ItemToCheck.getSerialNumber());
    }

    public boolean isItemInStore (Long ItemID)
    {
        return m_items.containsKey(ItemID);
    }


    public void addOrderToStoreHistory (Order NewOrder)
    {
        if (NewOrder.get) //check if order contine me...
        //todo check exception
        m_OrderHistory.add(NewOrder);
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

            for (Item curItem : m_items.values()) {
                res.append(i++ +". "+curItem+ " Cost:" + m_itemsPrices.get(curItem.getSerialNumber()));
                if (m_ItemsSold.containsKey(curItem.getSerialNumber()))
                    res.append(" and was Sold " + m_ItemsSold.get(curItem.getSerialNumber()) + " times.\n");
                else
                    res.append(" and was never sold in store");
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

        for (Order curOrder : m_OrderHistory)
        {
            res.append(i++ +". " +curOrder);
        }
        return res.toString();
    }

    public boolean equals(Store o) {
        if (this == o) return true;
        return (this.getStoreID() == o.getStoreID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_locationCoordinate, m_StoreID, m_profitFromShipping, m_items, m_itemsPrices, m_OrderHistory, m_Name, PPK);
    }
}
