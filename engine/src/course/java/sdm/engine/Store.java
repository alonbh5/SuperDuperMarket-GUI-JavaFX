package course.java.sdm.engine;
import course.java.sdm.exceptions.NegativePrice;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Store implements HasName, Coordinatable{

    private final Point m_locationCoordinate;  //todo this need final?
    private final long m_StoreID;
    private int m_profitFromShipping = 0;
    private Map<Long,Item> m_items = new HashMap<>();
    private Map<Long,Double> m_itemsPrices = new HashMap<>();
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

    //todo tostring hash and equals
}
