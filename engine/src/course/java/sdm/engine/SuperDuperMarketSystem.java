package course.java.sdm.engine;

import course.java.sdm.exceptions.*;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

//todo check all throws
public class SuperDuperMarketSystem {
    //todo access modifier this is the only public!
    //todo make serizible interface?

    public static final int MAX_COORDINATE = 50;
    public static final int MIN_COORDINATE = 1;
    private static long ItemSerialGenerator = 10000;
    private static long StoreSerialGenerator = 300000;
    private static long OrdersSerialGenerator = 5000000; //todo thing about what to do - XML chooses the Serial somethinms

    private Map<Long,ProductInSystem> m_ItemsInSystem = new HashMap<>(); //todo array or map
    //private Map<Long,Integer> m_AmountOfTimeItemHasSold = new HashMap<>();
    private Map<Point,Coordinatable> m_SystemGrid = new HashMap<>(); //all the shops
    private Map<Long,Order> m_OrderHistory = new HashMap<>(); //all the shops
    private Map<Long,Store> m_StoresInSystem = new HashMap<>(); //Todo - Merge this shit
    boolean locked = true;


    public static double CalculatePPK (Store FromStore, Point curLocation)
    {
        return (double)FromStore.getPPK() * FromStore.getCoordinate().distance(curLocation);
    }

    public double CalculatePPK (Long FromStoreID, Point curLocation)
    {
        return CalculatePPK(getStoreByID(FromStoreID),curLocation);
    }

//    public void AddNewItem (long i_serialNumber,String i_Name, Item.payByMethod e_howItsPaid)
//    {
//        Item newItem = new Item(i_serialNumber,i_Name,e_howItsPaid);
//        AddNewItem(newItem);
//    }

    public void AddNewItem (Item newItem)
    {
        if (m_ItemsInSystem.containsKey(newItem.getSerialNumber()))
            throw (new KeyAlreadyExistsException("The Item Serial Number " + newItem.getSerialNumber() + " Exist already in system "));

        ProductInSystem newProductSystem = new ProductInSystem(newItem);
        m_ItemsInSystem.put(newItem.getSerialNumber(),newProductSystem);
    }

//    public void AddNewOrder (Long i_serialNumber,Point m_Location, Date m_Date)
//    {
//        Order newOrder = new Order(i_serialNumber,m_Date,m_Location);
//        AddNewOrder(newOrder);
//    }


    public void AddNewOrder (Order newOrder)
    {
        //after the order is done
        //todo check that when i create order the coordanite of store is good....
        if (m_OrderHistory.containsKey(newOrder.getOrderSerialNumber()))
            throw (new KeyAlreadyExistsException("The Serial Number " + newOrder.getOrderSerialNumber() + " Exist already in system "));

        m_OrderHistory.put(newOrder.getOrderSerialNumber(),newOrder);
        UpdateShippingProfitAfterOrder(newOrder);
    }

    private void UpdateShippingProfitAfterOrder(Order newOrder) {
        Set<Store> AllStoresFromOrder = newOrder.getStoreSet();

        for (Store curStore : AllStoresFromOrder) {
            curStore.newShippingFromStore(CalculatePPK(curStore,newOrder.getCoordinate()));
        }
    }

//    public void AddNewStore (Long i_serialNumber,Point i_locationCoordinate,String m_Name, int i_PPK)
//    {
//        Store newStore = new Store(i_serialNumber,i_locationCoordinate,m_Name,i_PPK);
//        AddNewStore (newStore);
//    }

    public void AddNewStore (Store newStore)
    {
        if (m_SystemGrid.containsKey(newStore.getCoordinate()))
            throw (new KeyAlreadyExistsException("There is a Store at Coordinate (" + newStore.getCoordinate() + ") in system "));
        if (m_StoresInSystem.containsKey(newStore.getStoreID()))
            throw (new KeyAlreadyExistsException("The Serial Number " + newStore.getStoreID() + " Already Exist in system "));
        if (!isCoordinateInRange(newStore.getCoordinate()))
            throw (new PointOutOfGridException(newStore.getCoordinate()));

        m_SystemGrid.put(newStore.getCoordinate(),newStore);
        m_StoresInSystem.put(newStore.getStoreID(),newStore);
    }

    private Store getStoreByID (Long StoreID)
    {
        if (isStoreInSystem(StoreID))
            return m_StoresInSystem.get(StoreID);
        throw  ( new InvalidKeyException("Store #"+StoreID+" is not in System"));
    }

    public boolean isItemInSystem (long i_serialNumber)
    {
        return m_ItemsInSystem.containsKey(i_serialNumber);
    }

    public boolean isStoreInSystem (long i_serialNumber)
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

    public Item.payByMethod getPayingMethod (Long itemID)
    {
        if (!isItemInSystem(itemID))
            throw (new RuntimeException("Item Key #"+itemID+" is not is System"));

        return (m_ItemsInSystem.get(itemID).getItem().PayBy);

    }

    public double getAvgPriceForItem (Long ItemID)
    {
        return m_StoresInSystem.values().stream()
                .filter(t->t.isItemInStore(ItemID))
                .mapToDouble(t->t.getPriceForItem(ItemID))
                .average().getAsDouble();
    }

    public void addItemToStore (Long StoreID,ProductInStore productToAdd)
    {
        try {
            Store storeToAddTo = m_StoresInSystem.get(StoreID);
            storeToAddTo.addItemToStore(productToAdd);
            m_ItemsInSystem.get(productToAdd.getSerialNumber()).addSellingStore();
        }
        catch (Exception e){
            //todo catch
        }
    }

    public void addProductToOrder (Long OrderID,ProductInOrder productToAdd)
    {
        try {
            Order OrderToAddTo = m_OrderHistory.get(OrderID);
            OrderToAddTo.addProductToOrder(productToAdd);
            m_ItemsInSystem.get(productToAdd.getSerialNumber()).addTimesSold(productToAdd.getAmountByPayingMethod()); //todo this is by tmount bougt
            //if 2 shampoo => +2 , if 4.5 apples => +1
        }
        catch (Exception e){
            //todo catch
        }
    }


    public List<String> getListOfAllStoresInSystem () //todo return string Builder?
    {
        if (locked)
            throw new NoValidXMLException();

        StringBuilder str = new StringBuilder();
        List<String> res = new ArrayList<>();

        for (Store CurStore : m_StoresInSystem.values())
            res.add(CurStore.toString());

        return res;
    }

    public List<String> getListOfAllItems ()
    {
        if (locked)
            throw new NoValidXMLException();

        StringBuilder str = new StringBuilder();
        List<String> res = new ArrayList<>();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (ProductInSystem curItem : m_ItemsInSystem.values())
        {
            str.append(curItem.getItem().toString());
            str.append("Being Sold in "+curItem.getNumberOfSellingStores() +" Stores. \n");
            str.append("Average Price is : "+df.format(getAvgPriceForItem(curItem.getSerialNumber()))); //todo all avg needs to be 2 digit
            str.append("\nWas sold  : "+curItem.getAmountOfItemWasSold() +" times.");
            res.add(str.toString());
            str = null;
        }

        return res;
    }

    public List<String> getListOfAllOrderInSystem() {
        if (locked)
            throw new NoValidXMLException();


        List<String> res = new ArrayList<>();

        for (Order CurOrder : m_OrderHistory.values())
            res.add(CurOrder.toString());

        return res;
    }

    public void TryingFIle (String str)    {


    }
}
