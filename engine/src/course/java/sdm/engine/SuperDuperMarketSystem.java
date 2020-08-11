package course.java.sdm.engine;

import course.java.sdm.exceptions.*;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.util.*;

//todo check all throws
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
        Item newItem = new Item(i_serialNumber,i_Name,e_howItsPaid);
        AddNewItem(newItem);
    }

    public void AddNewItem (Item newItem)
    {
        if (m_ItemsInSystem.containsKey(newItem.getSerialNumber()))
            throw (new KeyAlreadyExistsException("The Item Serial Number " + newItem.getSerialNumber() + " Exist already in system "));

        m_ItemsInSystem.put(newItem.getSerialNumber(),newItem);
    }

    public void AddNewOrder (Long i_serialNumber,Point m_Location, Date m_Date)
    {
        Order newOrder = new Order(i_serialNumber,m_Date,m_Location);
        AddNewOrder(newOrder);
    }

    public void AddNewOrder (Order newOrder)
    {
        if (m_OrderHistory.containsKey(newOrder.getSerialNumber()))
            throw (new KeyAlreadyExistsException("The Serial Number " + newOrder.getSerialNumber() + " Exist already in system "));

        m_OrderHistory.put(newOrder.getSerialNumber(),newOrder);
    }

    public void AddNewStore (Long i_serialNumber,Point i_locationCoordinate,String m_Name, int i_PPK)
    {
        Store newStore = new Store(i_serialNumber,i_locationCoordinate,m_Name,i_PPK);
        AddNewStore (newStore);
    }

    public void AddNewStore (Store newStore)
    {
        if (m_SystemGrid.containsKey(newStore.getCoordinate()))
            throw (new KeyAlreadyExistsException("There is a Store at Coordinate (" + newStore.getCoordinate() + ") in system "));
        if (m_StoresInSystem.containsKey(newStore.getStoreID()))
            throw (new KeyAlreadyExistsException("The Serial Number " + newStore.getStoreID() + " Exist already in system "));
        if (!isCoordinateInRange(newStore.getCoordinate()))
            throw (new PointOutOfGridException(newStore.getCoordinate()));

        m_SystemGrid.put(newStore.getCoordinate(),newStore);
        m_StoresInSystem.put(newStore.getStoreID(),newStore);
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

    public static boolean isCoordinateInRange(Point Coordinate)
    {
        return (((Coordinate.x <= MAX_COORDINATE) && (Coordinate.x >= MIN_COORDINATE) && ((Coordinate.y <= MAX_COORDINATE) && (Coordinate.y >= MIN_COORDINATE))));
    }

}
