package course.java.sdm.engine;
import course.java.sdm.classesForUI.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.generatedClasses.*;
import javax.management.openmbean.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.util.*;
import java.util.List;


public class SuperDuperMarketSystem {

    public static final int MAX_COORDINATE = 50;
    public static final int MIN_COORDINATE = 1;
    private static long UsersSerialGenerator = 1000;
    private static long ItemSerialGenerator = 20000;
    private static long StoreSerialGenerator = 300000;
    private static long OrdersSerialGenerator = 4000000;

    private Map<Long,ProductInSystem> m_ItemsInSystem = new HashMap<>();
    private Map<Long,Customer> m_CustomersInSystem = new HashMap<>();
    private Map<Point,Coordinatable> m_SystemGrid = new HashMap<>(); //all the shops
    private Map<Long,Order> m_OrderHistory = new HashMap<>(); //all the shops
    private Map<Long,Store> m_StoresInSystem = new HashMap<>();
    private Order m_tempDynamicOrder = null;
    private boolean locked = true;


    static double CalculatePPK(Store FromStore, Point curLocation)   {
        return (double)FromStore.getPPK() * FromStore.getCoordinate().distance(curLocation);
    }

    public double CalculatePPK (Long FromStoreID, Point curLocation)  {
        return CalculatePPK(getStoreByID(FromStoreID),curLocation);
    }

    public double CalculateDistance(long storeID, Point curLocation) {
        return   getStoreByID(storeID).getCoordinate().distance(curLocation);
    }

    //boolean isX


    public boolean isItemInSystem (long i_serialNumber)
    {
        return m_ItemsInSystem.containsKey(i_serialNumber);
    }

    public boolean isStoreInSystem (long i_serialNumber)
    {
        return m_StoresInSystem.containsKey(i_serialNumber);
    }

    public boolean isCustomerInSystem (long i_serialNumber)
    {
        return m_CustomersInSystem.containsKey(i_serialNumber);
    }

    public boolean isOrderInSystem (long i_serialNumber)
    {
        return m_OrderHistory.containsKey(i_serialNumber);
    }

    public boolean isLocationTaken (Point pointInGrid)
    {
        return m_SystemGrid.containsKey(pointInGrid);
    }

    public static boolean isCoordinateInRange(Point Coordinate)  {
        return (((Coordinate.x <= MAX_COORDINATE) && (Coordinate.x >= MIN_COORDINATE) && ((Coordinate.y <= MAX_COORDINATE) && (Coordinate.y >= MIN_COORDINATE))));
    }

    public boolean isItemSoldInStore(Long storeID,Long itemID) {
        Store store = getStoreByID(storeID);
        return store.isItemInStore(itemID);
    }

    public boolean isLocked() {
        return locked;
    }

    //getters

    private Store getStoreByID (Long StoreID) throws InvalidKeyException  {
        if (isStoreInSystem(StoreID))
            return m_StoresInSystem.get(StoreID);
        throw  ( new InvalidKeyException("Store #"+StoreID+" is not in System"));
    }

    private ProductInSystem getItemByID (Long ItemID) throws InvalidKeyException  {
        if (isItemInSystem(ItemID))
            return m_ItemsInSystem.get(ItemID);
        throw  ( new InvalidKeyException("Item #"+ItemID+" is not in System"));
    }

    public int getAmountOfItemsInSystem ()
    {
        return m_ItemsInSystem.size();
    } //todo propties?

    public int getAmountOfOrdersInSystem ()
    {
        return m_OrderHistory.size();
    } //todo propties?

    public int getAmountOfStoresInSystem ()
    {
        return m_SystemGrid.size();
    } //todo propties?

    private double getAvgPriceForItem (Long ItemID)   {
        return Arrays.stream(m_StoresInSystem.values().stream()
                .filter(t -> t.isItemInStore(ItemID))
                .mapToDouble(t -> t.getPriceForItem(ItemID)).toArray())
                .average().getAsDouble();
    }

    public List<StoreInfo> getListOfAllStoresInSystem () throws NoValidXMLException    {
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

    public List<ItemInfo> getListOfAllItems () throws NoValidXMLException    {
        if (locked)
            throw new NoValidXMLException();

        StringBuilder str = new StringBuilder();
        List<ItemInfo> res = new ArrayList<>();

        for (ProductInSystem curItem : m_ItemsInSystem.values())
        {
            Item theItemObj = curItem.getItem();
            ItemInfo newItem = new ItemInfo(theItemObj.getSerialNumber(),theItemObj.getName()
                    ,theItemObj.getPayBy().toString(),getAvgPriceForItem(curItem.getSerialNumber()),
                            curItem.getNumberOfSellingStores(),curItem.getAmountOfItemWasSold());
            res.add(newItem);
        }

        return res;
    }

    public List<OrderInfo> getListOfAllOrderInSystem() throws NoValidXMLException    {
        if (locked)
            throw new NoValidXMLException();

        List<OrderInfo> res = new ArrayList<>();

        for (Order CurOrder : m_OrderHistory.values()) {
            res.add(createOrderInfo(CurOrder));
        }

        return res;
    }

    public double getItemPriceInStore(long storeID, long ItemID) {
            Store store = getStoreByID(storeID);
           return store.getPriceForItem(ItemID);
    }

    public StoreInfo getStoreInfoByID(Long storeID) throws InvalidKeyException   {
        if (isStoreInSystem(storeID))
            {
                Store curStore = m_StoresInSystem.get(storeID);
                List<ItemInStoreInfo> items = curStore.getItemList();
                List<OrdersInStoreInfo> orders = curStore.getOrderHistoryList();

                return new StoreInfo(curStore.getCoordinate(),curStore.getStoreID(),
                        curStore.getName(),curStore.getPPK(),items,orders, curStore.getProfitFromShipping());

            }
        throw  (new InvalidKeyException("Store #"+storeID+" is not in System"));

    }

    public ItemInfo getItemInfo(long itemID) {
        if (isItemInSystem(itemID))
        {
            ProductInSystem curItem = m_ItemsInSystem.get(itemID);
            Item theItemObj = curItem.getItem();
            return new ItemInfo(theItemObj.getSerialNumber(),theItemObj.getName()
                    ,theItemObj.getPayBy().toString(),getAvgPriceForItem(curItem.getSerialNumber()),
                    curItem.getNumberOfSellingStores(),curItem.getAmountOfItemWasSold());

        }
        return null;
    }

    private Store getMinSellingStoreForItem(Long serialNumber) {
        return m_ItemsInSystem.get(serialNumber).getMinSellingStore();
    }

    public int getPPK(Long storeID) {
        return m_StoresInSystem.get(storeID).getPPK();
    }

    //adding and deleting info

    public void addItemToStore(long storeID, ItemInStoreInfo itemInStoreInfo) throws DuplicateItemInStoreException, StoreItemNotInSystemException, NegativePriceException {
        Store storeByID = getStoreByID(storeID);
        long itemID = itemInStoreInfo.serialNumber;
        double Price = itemInStoreInfo.PriceInStore;
        ProductInSystem itemByID = getItemByID(itemID);

        if (!isItemInSystem(itemID))
            throw new StoreItemNotInSystemException(itemID,storeID);

        if (storeByID.isItemInStore(itemID))
            throw new DuplicateItemInStoreException(storeID);

        if (Price<=0)
            throw new NegativePriceException(Price);

        ProductInStore newProduct = new ProductInStore(itemByID.getItem(),Price,storeByID);
        addItemToStore(storeID,newProduct);

        if (Price < m_ItemsInSystem.get(itemID).getMinSellingStore().getPriceForItem(itemID)) //update min selling store
            m_ItemsInSystem.get(itemID).setMinSellingStore(m_StoresInSystem.get(storeID));

    }

    public OrderInfo addDynamicOrderToSystem (Collection<ItemInOrderInfo> itemsChosen,CustomerInfo curUser, Date OrderDate) throws InvalidKeyException, PointOutOfGridException, ItemIsNotSoldAtAllException, CustomerNotInSystemException {
        //NOTE - You need to use approveDynamicOrder to insert to system after
        if (!isCustomerInSystem(curUser.ID))
            throw (new CustomerNotInSystemException(curUser.ID));

        Customer curCustomer = m_CustomersInSystem.get(curUser.ID);

        Store minSellingStore;
        ProductInSystem itemInSys;
        Order newOrder = createEmptyOrder(curCustomer,OrderDate);

        for (ItemInOrderInfo curItem : itemsChosen) {
            if (!isItemInSystem(curItem.serialNumber))
                throw new ItemIsNotSoldAtAllException(curItem.serialNumber,"Item is not in system!");
            if (curItem.amountBought <= 0)
                throw (new RuntimeException("Amount is not Allowed.." + curItem.amountBought));

            itemInSys = getItemByID(curItem.serialNumber); //get the item in sys
            minSellingStore = getMinSellingStoreForItem (itemInSys.getSerialNumber()); //get min selling store
            ProductInOrder newItem = new ProductInOrder(minSellingStore.getItemInStore(curItem.serialNumber)); //create prod in order
            newItem.setAmountBought(curItem.amountBought); //set how much you want
            newOrder.addProductToOrder(newItem); //added it to order
        }

        m_tempDynamicOrder = newOrder;
        return createOrderInfo(newOrder);
    }

    private void addItemToStore (Long StoreID,ProductInStore productToAdd) throws NegativePriceException {

        Store storeToAddTo = m_StoresInSystem.get(StoreID);
        storeToAddTo.addItemToStore(productToAdd);
        m_ItemsInSystem.get(productToAdd.getSerialNumber()).addSellingStore();
    }

    public void addStaticOrderToSystem(Collection<ItemInOrderInfo> itemsChosen, StoreInfo storeChosen, CustomerInfo curUser, Date OrderDate) throws PointOutOfGridException, StoreDoesNotSellItemException, CustomerNotInSystemException {
        if (!m_StoresInSystem.containsKey(storeChosen.StoreID))
            throw (new RuntimeException("Store ID #"+storeChosen.StoreID+" is not in System"));
        if (itemsChosen.isEmpty())
            throw (new RuntimeException("Empty List"));
        if (!isCustomerInSystem(curUser.ID))
            throw (new CustomerNotInSystemException(curUser.ID));

        Customer curCustomer = m_CustomersInSystem.get(curUser.ID);


        Order newOrder = createEmptyOrder(curCustomer,OrderDate);
        Store curStore = m_StoresInSystem.get(storeChosen.StoreID);


        for (ItemInOrderInfo curItem : itemsChosen) {
            if (!curStore.isItemInStore(curItem.serialNumber))
                throw (new StoreDoesNotSellItemException(curItem.serialNumber));
            if (curItem.amountBought <= 0)
                throw (new RuntimeException("Amount is not Allowed.." + curItem.amountBought));

            ProductInStore curProd = curStore.getProductInStoreByID(curItem.serialNumber);
            ProductInOrder newItem = new ProductInOrder(curProd);
            newItem.setAmountBought(curItem.amountBought);
            newOrder.addProductToOrder(newItem);
        }

        for (ProductInOrder curItem :newOrder.getBasket())
            m_ItemsInSystem.get(curItem.getSerialNumber()).addTimesSold(curItem.getAmountByPayingMethod());

        m_OrderHistory.put(newOrder.getOrderSerialNumber(),newOrder);
        updateShippingProfitAfterOrder(newOrder); //update shipping profit
        updateSoldCounterInStore(newOrder); // updated the counter of item in the store (how many times has been sold)
        curStore.addOrderToStoreHistory(newOrder);
    }

    private OrderInfo createOrderInfo(Order CurOrder) {
        Set<Store> stores = CurOrder.getStoreSet();
        List<String> storesList = new ArrayList<>();

        Set<ProductInOrder> Items = CurOrder.getBasket();
        List<ItemInOrderInfo> itemsInOrder = new ArrayList<>();

        for (Store curStore : stores)
            storesList.add("Store Name: "+curStore.getName()+ " #"+curStore.getStoreID());

        for (ProductInOrder curProd : Items)
            itemsInOrder.add(new ItemInOrderInfo(curProd.getSerialNumber(),curProd.getProductInStore().getItem().getName(),
                    curProd.getPayBy().toString(),curProd.getProductInStore().getStore().getStoreID()
                    ,curProd.getAmount(),curProd.getProductInStore().getPricePerUnit(),curProd.getPriceOfTotalItems()));

        int ppk =0;
        double distance = 0;
        if (stores.size() == 1)
            for (Store curStore : stores) {
                ppk = curStore.getPPK();
                distance = CalculatePPK(curStore,CurOrder.getCoordinate());
            }

        CustomerInfo UserInOrder = createCustomerInfo(CurOrder.getCostumer());

        return new OrderInfo(CurOrder.getOrderSerialNumber(),CurOrder.getDate(),
                storesList,itemsInOrder,CurOrder.getTotalPrice(),CurOrder.getShippingPrice(),CurOrder.getItemsPrice(),CurOrder.getAmountOfItems(),distance,ppk,UserInOrder);
    }

    private CustomerInfo createCustomerInfo (Customer user) {
        return new CustomerInfo(user.getName(),user.getId(),user.getCoordinate(),user.getAvgPriceOfShipping(),user.getAvgPriceOfOrdersWithoutShipping(),user.getAmountOFOrders());
    }

    private void crateNewStoreInSystem(SDMStore store) throws PointOutOfGridException, DuplicatePointOnGridException, NegativePriceException, StoreItemNotInSystemException, DuplicateItemInStoreException, StoreDoesNotSellItemException, IllegalOfferException, NegativeQuantityException, NoOffersInDiscountException {
        Point StoreLocation = new Point(store.getLocation().getX(), store.getLocation().getY());
        if (!isCoordinateInRange(StoreLocation))
            throw new PointOutOfGridException(StoreLocation);
        if (isLocationTaken(StoreLocation))
            throw new DuplicatePointOnGridException(StoreLocation);

        Store newStore = new Store((long) store.getId(), StoreLocation, store.getName(), store.getDeliveryPpk());
        ProductInSystem sysItem;

        for (SDMSell curItem : store.getSDMPrices().getSDMSell()) {
            Long ItemID = (long) curItem.getItemId();
            double itemPrice = curItem.getPrice();

            if (itemPrice <= 0)
                throw new NegativePriceException(itemPrice);
            if (!isItemInSystem(ItemID))
                throw new StoreItemNotInSystemException(ItemID, newStore.getStoreID());
            if (newStore.isItemInStore(ItemID))
                throw new DuplicateItemInStoreException(ItemID);

            Item BaseItem = m_ItemsInSystem.get(ItemID).getItem();
            ProductInStore newItemForStore = new ProductInStore(BaseItem, itemPrice, newStore);
            newStore.addItemToStore(newItemForStore);
            sysItem = m_ItemsInSystem.get(ItemID);
            sysItem.addSellingStore();
            if (sysItem.getMinSellingStore() == null || itemPrice < sysItem.getMinSellingStore().getPriceForItem(BaseItem.getSerialNumber()))
                sysItem.setMinSellingStore(newStore);

        }
//todo check spaces and case sensetuve  "    sdas "  = "sdas"
        if (newStore.getItemList().isEmpty())
            throw new StoreDoesNotSellItemException(newStore.getStoreID());

        if (store.getSDMDiscounts() != null)
            for (SDMDiscount curDis : store.getSDMDiscounts().getSDMDiscount()) {
                if (!newStore.isItemInStore((long) curDis.getIfYouBuy().getItemId()))
                    throw new StoreDoesNotSellItemException("Item of Discount is not sold at store", curDis.getIfYouBuy().getItemId());
                if (curDis.getIfYouBuy().getQuantity() < 0)
                    throw new NegativeQuantityException(curDis.getIfYouBuy().getQuantity());
                if (curDis.getThenYouGet().getSDMOffer().isEmpty())
                    throw new NoOffersInDiscountException(curDis.getName());

                Discount.OfferType newOp = Discount.OfferType.IRRELEVANT;
                if (curDis.getThenYouGet().getOperator().equals("ONE-OF"))
                    newOp = Discount.OfferType.ONE_OF;
                if (curDis.getThenYouGet().getOperator().equals("ALL-OR-NOTHING"))
                    newOp = Discount.OfferType.ALL_OR_NOTHING;
                if (newOp.equals(Discount.OfferType.IRRELEVANT) && curDis.getThenYouGet().getSDMOffer().size() != 1)
                    throw new IllegalOfferException(curDis.getName());

                Item curItem = m_ItemsInSystem.get((long) curDis.getIfYouBuy().getItemId()).getItem();
                ProductYouBuy whatYouBuy = new ProductYouBuy(curItem, curDis.getIfYouBuy().getQuantity());
                Discount newDis = new Discount(newOp, curDis.getName(), whatYouBuy);

                for (SDMOffer offer : curDis.getThenYouGet().getSDMOffer()) {
                    if (offer.getForAdditional() < 0)
                        throw new NegativePriceException(offer.getForAdditional());
                    if (!newStore.isItemInStore((long) offer.getItemId()))
                        throw new StoreDoesNotSellItemException("Item of Discount is not sold at store", curDis.getIfYouBuy().getItemId());
                    if (offer.getQuantity() < 0)
                        throw new NegativeQuantityException(offer.getQuantity());
                    Item itemForCtor = m_ItemsInSystem.get((long) offer.getItemId()).getItem();

                    newDis.AddProductYouGet(new ProductYouGet(itemForCtor, offer.getQuantity(), offer.getForAdditional()));
                }
                newStore.addDiscount(newDis);
            }

        m_StoresInSystem.put(newStore.getStoreID(), newStore);
        m_SystemGrid.put(newStore.getCoordinate(), newStore);
    }

    private void crateNewItemInSystem(SDMItem item) throws WrongPayingMethodException {
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

    private void crateNewCustomerInSystem(SDMCustomer customer) throws PointOutOfGridException, DuplicatePointOnGridException {

        Point location = new Point(customer.getLocation().getX(),customer.getLocation().getY());

        if (!isCoordinateInRange(location))
            throw new PointOutOfGridException(location);
        if (isLocationTaken(location))
            throw new DuplicatePointOnGridException(location);
        Customer newUser = new Customer(customer.getId(),customer.getName(),location);
        m_CustomersInSystem.put(newUser.getIdNumber(),newUser);
        m_SystemGrid.put(newUser.getCoordinate(),newUser);
    }

    private Order createEmptyOrder (Customer customer, Date OrderDate) throws PointOutOfGridException {
        if (!isCoordinateInRange(customer.getCoordinate()))
            throw (new PointOutOfGridException(customer.getCoordinate()));
        if (m_SystemGrid.containsKey(customer.getCoordinate()))
            throw (new KeyAlreadyExistsException("There is a store at "+ customer.getCoordinate().toString()));

        while (m_OrderHistory.containsKey(OrdersSerialGenerator))
            OrdersSerialGenerator++;

        return new Order (customer,OrdersSerialGenerator++,OrderDate);
    }

    public void DeleteItemFromStore(long itemID, long storeID) throws InvalidKeyException, StoreDoesNotSellItemException, ItemIsNotSoldAtAllException { //bonus

        Store storeByID = getStoreByID(storeID);
        ProductInSystem itemByID = getItemByID(itemID);

        if (!storeByID.isItemInStore(itemID))
            throw new StoreDoesNotSellItemException(storeID);

        if (itemByID.getNumberOfSellingStores() == 1)
            throw new ItemIsNotSoldAtAllException(itemByID.getSerialNumber(), itemByID.getItem().getName());

        storeByID.DeleteItem(itemID);
        ProductInSystem productInSystem = m_ItemsInSystem.get(itemID);
        productInSystem.removeSellingStore();

        if (storeID == (productInSystem.getMinSellingStore().getStoreID()))  //update min selling store
            fineAndSetMinStore(productInSystem);

    }

    public void ChangePrice(long itemID, long storeID, double newPrice) throws NegativePriceException, StoreDoesNotSellItemException {
        Store storeByID = getStoreByID(storeID);

        if (!storeByID.isItemInStore(itemID))
            throw new StoreDoesNotSellItemException(storeID);
        if (newPrice<=0)
            throw new NegativePriceException(newPrice);

        double currentLowestPrice = m_ItemsInSystem.get(itemID).getMinSellingStore().getPriceForItem(itemID);
        storeByID.changePrice(itemID,newPrice);


        if (newPrice < currentLowestPrice) //case new price is lower then the current one
            m_ItemsInSystem.get(itemID).setMinSellingStore(m_StoresInSystem.get(storeID)); //this is the best place to buy
        else
        if (newPrice > currentLowestPrice && storeID == m_ItemsInSystem.get(itemID).getMinSellingStore().getStoreID()) //case we put more to the new price and it was the lowest before
            fineAndSetMinStore(getItemByID(itemID));

    }

    public void approveDynamicOrder()    {
        for (ProductInOrder curItem :m_tempDynamicOrder.getBasket())
            m_ItemsInSystem.get(curItem.getSerialNumber()).addTimesSold(curItem.getAmountByPayingMethod());

        m_OrderHistory.put(m_tempDynamicOrder.getOrderSerialNumber(),m_tempDynamicOrder);
        updateShippingProfitAfterOrder(m_tempDynamicOrder); //update shipping profit
        updateSoldCounterInStore(m_tempDynamicOrder); // updated the counter of item in the store (how many times has been sold)
        for (Store curStore : m_tempDynamicOrder.getStoreSet())
            curStore.addOrderToStoreHistory(m_tempDynamicOrder);
    }

    //files

    /*public void LoadOrderFromFile(String strPath) throws NoValidXMLException, IOException, FileNotFoundException, ClassNotFoundException, NegativePriceException, PathException {
        //was for bonus part 1
        if (locked)
            throw new NoValidXMLException();

        Path myPath = Paths.get(strPath);

        if (!strPath.contains(".dat"))
            throw new PathException("File Should be .dat File");
        ObjectInputStream in = null;
        FileInputStream OrderFile = null;
        File theFile = myPath.toFile();
        if (!theFile.exists())
            throw new FileNotFoundException();
        try {
            Collection<Order> NewOrders;
            OrderFile = new FileInputStream(theFile);
            in = new ObjectInputStream(OrderFile);
            NewOrders = (Collection<Order>) in.readObject();
            checkOrdersAndInsertToSystem(NewOrders);
        }finally {
            OrderFile.close();
            in.close();
        }

    }*/

    /*public void SaveOrdersToBin(String strPath) throws IOException, NoValidXMLException, PathException {
        //was for bonus part 1

        if (locked)
            throw new NoValidXMLException();

        Path myPath = Paths.get(strPath);
        ObjectOutputStream out=null;
        FileOutputStream newFile= null;

        if (!myPath.isAbsolute())
            throw new PathException("Please Enter Absolute Path");

        File myFile = new File(strPath+"\\Orders.dat");
        try {
            newFile = new FileOutputStream(myFile);
            out = new ObjectOutputStream(newFile);
            out.writeObject(new ArrayList(m_OrderHistory.values()));

        }finally {
            newFile.close();
            out.close();
        }
    }*/

    public void UploadInfoFromXML (String XMLPath) throws DuplicatePointOnGridException, DuplicateItemIDException, DuplicateItemInStoreException
            , DuplicateStoreInSystemException, ItemIsNotSoldAtAllException, NegativePriceException, PointOutOfGridException,
            StoreDoesNotSellItemException, StoreItemNotInSystemException, WrongPayingMethodException, NoValidXMLException, NoOffersInDiscountException, IllegalOfferException, NegativeQuantityException, DuplicateCustomerInSystemException {
        SuperDuperMarketDescriptor superDuperMarketDescriptor;
        try {
            superDuperMarketDescriptor = FileHandler.UploadFile(XMLPath);
        } catch (JAXBException e) {
            throw new NoValidXMLException();
        }
        copyInfoFromXMLClasses(superDuperMarketDescriptor);
    }










    private void updateShippingProfitAfterOrder(Order newOrder) {
        Set<Store> AllStoresFromOrder = newOrder.getStoreSet();
        Store storeInSysAndNotInFile;
        for (Store curStore : AllStoresFromOrder) {
            storeInSysAndNotInFile = getStoreByID(curStore.getStoreID());
            storeInSysAndNotInFile.newShippingFromStore(CalculatePPK(storeInSysAndNotInFile,newOrder.getCoordinate()));
        }
    }

    private void updateSoldCounterInStore(Order newOrder) {
        Set<ProductInOrder> AllItemsFromOrder = newOrder.getBasket();
        ProductInStore ProdInSysAndNotInFile;
        long itemID;
        for (ProductInOrder curItem : AllItemsFromOrder) {
            ProdInSysAndNotInFile = getStoreByID(curItem.getProductInStore().getStore().getStoreID()).getProductInStoreByID(curItem.getSerialNumber());
            ProdInSysAndNotInFile.addAmount(curItem.getAmountByPayingMethod());
        }
    }

    private void fineAndSetMinStore (ProductInSystem productInSystem) {
        long itemID = productInSystem.getSerialNumber();
        productInSystem.setMinSellingStore(null);
        for (Store curStore : m_StoresInSystem.values()) {
            if (curStore.isItemInStore(itemID)) {
                if (productInSystem.getMinSellingStore() == null)
                    productInSystem.setMinSellingStore(curStore);
                else if (curStore.getPriceForItem(itemID) < productInSystem.getMinSellingStore().getPriceForItem(itemID))
                    productInSystem.setMinSellingStore(curStore);
            }
        }
    }

    private void checkMissingItem() throws ItemIsNotSoldAtAllException {
        for (ProductInSystem curItem : m_ItemsInSystem.values())
            if (curItem.getNumberOfSellingStores() == 0)
                throw new ItemIsNotSoldAtAllException(curItem.getSerialNumber(),curItem.getItem().getName());
    }

    private void checkOrdersAndInsertToSystem(Collection<Order> newOrders) throws NegativePriceException {
        for (Order curOrder : newOrders){
            if(!isOrderInSystem(curOrder.getOrderSerialNumber())) { //if order is not in system already
                if (curOrder.getShippingPrice() > 0 && curOrder.getItemsPrice() > 0 && curOrder.getTotalPrice() > 0) { //and all prices are ok
                    m_OrderHistory.put(curOrder.getOrderSerialNumber(),curOrder);
                    updateShippingProfitAfterOrder(curOrder); //update shipping profit
                    updateSoldCounterInStore(curOrder); // updated the counter of item in the store (how many times has been sold)
                    for (ProductInOrder curItem :curOrder.getBasket())
                        m_ItemsInSystem.get(curItem.getSerialNumber()).addTimesSold(curItem.getAmountByPayingMethod());
                    for (Store curStore : curOrder.getStoreSet()) {
                        Store StoreInThisSys = getStoreByID(curStore.getStoreID());
                        StoreInThisSys.addOrderToStoreHistory(curOrder);
                    }
                }
                else
                    throw new NegativePriceException(curOrder.getTotalPrice());
            }
        }

    }

    private void copyInfoFromXMLClasses(SuperDuperMarketDescriptor superDuperMarketDescriptor) throws DuplicateCustomerInSystemException,NegativeQuantityException,IllegalOfferException,NoOffersInDiscountException,DuplicateStoreInSystemException, DuplicateItemIDException, DuplicateItemInStoreException, NegativePriceException, StoreItemNotInSystemException, DuplicatePointOnGridException, StoreDoesNotSellItemException, PointOutOfGridException, ItemIsNotSoldAtAllException, WrongPayingMethodException {

        Map<Long,ProductInSystem> tempItemsInSystem = m_ItemsInSystem;
        Map<Point,Coordinatable> tempSystemGrid = m_SystemGrid;
        Map<Long,Store> tempStoresInSystem = m_StoresInSystem;
        Map<Long,Order> tempOrderInSystem = m_OrderHistory;
        Map<Long,Customer> tempCustomerInSystem = m_CustomersInSystem;

        m_ItemsInSystem = new HashMap<>();
        m_SystemGrid = new HashMap<>();
        m_StoresInSystem = new HashMap<>();
        m_OrderHistory = new HashMap<>();
        m_CustomersInSystem = new HashMap<>();

        try {

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

            for (SDMCustomer customer : superDuperMarketDescriptor.getSDMCustomers().getSDMCustomer()) {
                if (!isCustomerInSystem(customer.getId()))
                    crateNewCustomerInSystem(customer);
                else throw new DuplicateCustomerInSystemException(customer.getId());
            }

            checkMissingItem();

        } catch (Exception e) //if from any reason xml was bad - restore data
        {
            m_ItemsInSystem= tempItemsInSystem;
            m_SystemGrid = tempSystemGrid;
            m_StoresInSystem=tempStoresInSystem;
            m_OrderHistory = tempOrderInSystem;
            m_CustomersInSystem = tempCustomerInSystem;
            throw e;
        }

        locked = false;
    }


}
