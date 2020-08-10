package course.java.sdm.engine;

import javafx.collections.transformation.SortedList;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.util.*;
import java.util.List;


public class SuperDuperMarketSystem {
    //todo access modifier this is the only public!

    public static final int MAX_COORDINATE = 50;
    public static final int MIN_COORDINATE = 1;
    private static long ItemSerialGenerator = 10000;
    private static long StoreSerialGenerator = 300000;
    private static long OrdersSerialGenerator = 5000000; //todo thing about what to do - XML chooses the Serial somethinms

    private Map<Long,Item> m_ItemsInSystem = new HashMap<>(); //todo array or map
    private Map<Point,Coordinatable> m_SystemGrid = new HashMap<>(); //all the shops
    private Map<Long,Order> m_OrderHistory = new HashMap<>(); //all the shops
    private Map<Long,Store> m_StoresInSystem = new HashMap<>(); //Todo - Merge this shit

    public static double CalculatePPK (Store FromStore, Point curLocation)
    {
        return (double)FromStore.getPPK() * FromStore.getCoordinate().distance(curLocation);
    }

    public void AddNewItem (long i_serialNumber,String i_Name, Item.payByMethod e_howItsPaid)
    {
        if (m_ItemsInSystem.containsKey(i_serialNumber))
            throw (new KeyAlreadyExistsException("The Serial Number " + i_serialNumber + " Exist already in system "));

        Item newItem = new Item(i_serialNumber,i_Name,e_howItsPaid);
        m_ItemsInSystem.put(i_serialNumber,newItem);
    }

    public void AddNewOrder (Long i_serialNumber,Customer m_Customer, Date m_Date)
    {
        if (m_OrderHistory.containsKey(i_serialNumber))
            throw (new KeyAlreadyExistsException("The Serial Number " + i_serialNumber + " Exist already in system "));

        Order newOrder = new Order(i_serialNumber,m_Customer,m_Date);
        m_OrderHistory.put(i_serialNumber,newOrder);
    }

    public void AddNewStore (Long i_serialNumber,Point i_locationCoordinate,String m_Name, int i_PPK)
    {
        if (m_SystemGrid.containsKey(i_locationCoordinate))
            throw (new KeyAlreadyExistsException("There is a Store at Coordinate (" + i_locationCoordinate + ") in system "));
        if (m_StoresInSystem.containsKey(i_serialNumber))
            throw (new KeyAlreadyExistsException("The Serial Number " + i_serialNumber + " Exist already in system "));

        Store newStore = new Store(i_serialNumber,i_locationCoordinate,m_Name,i_PPK);
        m_SystemGrid.put(i_locationCoordinate,newStore);
        m_StoresInSystem.put(i_serialNumber,newStore);
    }



    public boolean isItemInSystem (long i_serialNumber)
    {
        return m_ItemsInSystem.containsKey(i_serialNumber);
    }

    public boolean isStoreSystem (long i_serialNumber)
    {
        return m_StoresInSystem.containsKey(i_serialNumber);
    }

    public boolean isOrderInSystem (long i_serialNumber)
    {
        return m_OrderHistory.containsKey(i_serialNumber);
    }

    public boolean isLocationTaken (Point pointInGrid)
    {
        return m_SystemGrid.containsKey(pointInGrid);
    }

    public int getAmountOfItemsInSystem ()
    {
        return m_ItemsInSystem.size();
    }

    public int getAmountOfOrdersInSystem ()
    {
        return m_OrderHistory.size();
    }

    public int getAmountOfStoresInSystem ()
    {
        return m_SystemGrid.size();
    }

}
