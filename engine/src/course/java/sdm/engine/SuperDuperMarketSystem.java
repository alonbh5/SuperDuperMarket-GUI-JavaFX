package course.java.sdm.engine;

import course.java.sdm.classesForUI.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.generatedClasses.*;
import javax.management.openmbean.*;
import java.awt.*;
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

    public void AddNewItem (Item newItem)
    {
        if (m_ItemsInSystem.containsKey(newItem.getSerialNumber()))
            throw (new KeyAlreadyExistsException("The Item Serial Number " + newItem.getSerialNumber() + " Exist already in system "));

        ProductInSystem newProductSystem = new ProductInSystem(newItem);
        m_ItemsInSystem.put(newItem.getSerialNumber(),newProductSystem);
    }

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
            m_ItemsInSystem.get(productToAdd.getSerialNumber()).addTimesSold(productToAdd.getAmountByPayingMethod());
            //if 2 shampoo => +2 , if 4.5 apples => +1
        }
        catch (Exception e){
            //todo catch
        }
    }


    public List<StoreInfo> getListOfAllStoresInSystem ()
    {
        if (locked)
            throw new NoValidXMLException();

        StringBuilder str = new StringBuilder();
        List<StoreInfo> res = new ArrayList<>();

        for (Store CurStore : m_StoresInSystem.values()){
            List<ItemInStoreInfo> items = CurStore.getItemList();
            List<OrdersInStoreInfo> orders = CurStore.getOrderHistoryList();

            StoreInfo newStore = new StoreInfo(CurStore.getCoordinate(),CurStore.getStoreID(),
                    CurStore.getName(),CurStore.getPPK(),items,orders, CurStore.getProfitFromShipping());
            res.add(newStore);
        }


        return res;
    }

    public List<ItemInfo> getListOfAllItems ()
    {
        if (locked)
            throw new NoValidXMLException();

        StringBuilder str = new StringBuilder();
        List<ItemInfo> res = new ArrayList<>();

        for (ProductInSystem curItem : m_ItemsInSystem.values())
        {
            Item theItemObj = curItem.getItem();
            ItemInfo newItem = new ItemInfo(theItemObj.getSerialNumber(),theItemObj.getName()
                    ,theItemObj.PayBy.toString(),getAvgPriceForItem(curItem.getSerialNumber()),
                            curItem.getNumberOfSellingStores(),curItem.getAmountOfItemWasSold());
            res.add(newItem);
        }

        return res;
    }

    public List<OrderInfo> getListOfAllOrderInSystem() {
        if (locked)
            throw new NoValidXMLException();

        List<OrderInfo> res = new ArrayList<>();

        for (Order CurOrder : m_OrderHistory.values()) {

            Set<Store> stores = CurOrder.getStoreSet();
            List<String> storesList = new ArrayList<>();

            for (Store curStore : stores)
                storesList.add("Store Name: "+curStore.getName()+ " #"+curStore.getStoreID());

            OrderInfo newOrder = new OrderInfo(CurOrder.getOrderSerialNumber(),CurOrder.getDate(),
                    storesList,CurOrder.getTotalPrice(),CurOrder.getShippingPrice(),CurOrder.getItemsPrice(),CurOrder.getAmountOfItems());
            res.add(newOrder);
        }

        return res;
    }

    public boolean UploadInfoFromXML (String XMLPath) //todo throw exception from method...
    {
        SuperDuperMarketDescriptor superDuperMarketDescriptor = InfoLoader.TryingFIle(XMLPath);
        CopyInfoFromXMLClasses(superDuperMarketDescriptor);
        return !locked;
    }

    private void CopyInfoFromXMLClasses(SuperDuperMarketDescriptor superDuperMarketDescriptor) {

        for (SDMItem Item : superDuperMarketDescriptor.getSDMItems().getSDMItem()) {
            if (!isItemInSystem(Item.getId()))
                crateNewItemInSystem(Item);
            else throw new DuplicateItemIDException(Item.getId());
        }

        for (SDMStore Store : superDuperMarketDescriptor.getSDMStores().getSDMStore()) {
            if (!isStoreInSystem(Store.getId()))
                crateNewStoreInSystem(Store);
            else throw new DuplicateStoreInSystemException(Store.getId());
        }
    }

    private void crateNewStoreInSystem(SDMStore store) {
        Point StoreLocation = new Point(store.getLocation().getX(),store.getLocation().getY());
        if (!isCoordinateInRange(StoreLocation))
            throw new PointOutOfGridException(StoreLocation);
        Store newStore = new Store ((long)store.getId(),StoreLocation,store.getName(),store.getDeliveryPpk());

        for (SDMSell curItem : store.getSDMPrices().getSDMSell()){
            Long ItemID = (long)curItem.getItemId();
            double itemPrice = curItem.getPrice();

            if (itemPrice<=0)
                throw new NegativePriceException(itemPrice);
            if (!isItemInSystem(ItemID))
                throw new StoreItemNotInSystemException(ItemID);
            if (newStore.isItemInStore(ItemID))
                throw new DuplicateItemInStoreException(ItemID);

            Item BaseItem = m_ItemsInSystem.get(ItemID).getItem();
            ProductInStore newItemForStore = new ProductInStore(BaseItem,itemPrice,newStore);
        }

        m_StoresInSystem.put(newStore.getStoreID(),newStore);
    }

    private void crateNewItemInSystem(SDMItem item) {
        Item.payByMethod ePayBy;

        if (item.getPurchaseCategory().equals("Weight"))
            ePayBy = Item.payByMethod.WEIGHT;
        else
            if (item.getPurchaseCategory().equals("Quantity"))
                 ePayBy = Item.payByMethod.AMOUNT;
            else
                throw new WrongPayingMethodException(item.getPurchaseCategory());

        Item newBaseItem = new Item ((long)item.getId(),item.getName(),ePayBy);
        ProductInSystem newItem = new ProductInSystem(newBaseItem);
        m_ItemsInSystem.put(newItem.getSerialNumber(),newItem);
    }
}
