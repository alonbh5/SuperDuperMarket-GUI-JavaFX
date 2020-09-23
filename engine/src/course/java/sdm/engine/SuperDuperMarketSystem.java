package course.java.sdm.engine;
import course.java.sdm.classesForUI.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.generatedClasses.*;
import course.java.sdm.gui.MainMenu.MainMenuController;
import javafx.concurrent.Task;

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

    private Task<Boolean> currentRunningTask;
    private final MainMenuController controller;

    private Map<Long,ProductInSystem> m_ItemsInSystem = new HashMap<>();
    private Map<Long,Customer> m_CustomersInSystem = new HashMap<>();
    private Map<Point,Coordinatable> m_SystemGrid = new HashMap<>(); //all the shops
    private Map<Long,Order> m_OrderHistory = new HashMap<>(); //all the shops
    private Map<Long,Store> m_StoresInSystem = new HashMap<>();
    private Order m_tempOrder = null;
    private boolean locked = true;

    public SuperDuperMarketSystem(MainMenuController controller) {
        this.controller = controller;
    }

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

    public Collection<StoreInfo> getListOfAllStoresInSystem () throws NoValidXMLException    {
        if (locked)
            throw new NoValidXMLException();

        List<StoreInfo> res = new ArrayList<>();

        for (Store CurStore : m_StoresInSystem.values()){
            res.add(getStoreInfoByID(CurStore.getStoreID()));
        }


        return res;
    }

    public List<CustomerInfo> getListOfAllCustomerInSystem () throws NoValidXMLException    {
        if (locked)
            throw new NoValidXMLException();

        List<CustomerInfo> res = new ArrayList<>();

        for (Customer curCustomer : m_CustomersInSystem.values()){
            CustomerInfo newCustomer = new CustomerInfo(curCustomer.getName(),
                    curCustomer.getIdNumber()
                    ,curCustomer.getCoordinate()
                    ,curCustomer.getAvgPriceOfShipping()
                    ,curCustomer.getAvgPriceOfOrdersWithoutShipping()
                    ,curCustomer.getAmountOFOrders());
            res.add(newCustomer);
        }


        return res;
    }

    public Collection<ItemInfo> getListOfAllItems () throws NoValidXMLException    {
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

    public Collection<OrderInfo> getListOfAllOrderInSystem() throws NoValidXMLException    {
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
                List<OrderInfo> orders = curStore.getOrderHistoryList(); //todo this need to return new info
                List<DiscountInfo> discounts = curStore.getDiscountsList();

                return new StoreInfo(curStore.getCoordinate(),
                        curStore.getStoreID(),
                        curStore.getProfitFromShipping(),
                        items,orders,discounts
                        ,curStore.getName(),curStore.getPPK());

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

    private void addItemToStore (Long StoreID,ProductInStore productToAdd) throws NegativePriceException {

        Store storeToAddTo = m_StoresInSystem.get(StoreID);
        storeToAddTo.addItemToStore(productToAdd);
        m_ItemsInSystem.get(productToAdd.getSerialNumber()).addSellingStore();
    }



    public List<DiscountInfo> getDiscountsFromStaticOrder (Collection<ItemInOrderInfo> itemsChosen, StoreInfo storeChosen, CustomerInfo curUser, Date OrderDate) throws PointOutOfGridException, StoreDoesNotSellItemException, CustomerNotInSystemException, OrderIsNotForThisCustomerException {
        //create temp static order, return entitled discount..

        if (!m_StoresInSystem.containsKey(storeChosen.StoreID))
            throw (new RuntimeException("Store ID #"+storeChosen.StoreID+" is not in System"));
        if (itemsChosen.isEmpty())
            throw (new RuntimeException("Empty List"));
        if (!isCustomerInSystem(curUser.ID))
            throw (new CustomerNotInSystemException(curUser.ID));

        Customer curCustomer = m_CustomersInSystem.get(curUser.ID);
        Store curStore = m_StoresInSystem.get(storeChosen.StoreID);
        Order newOrder = createEmptyOrder(curCustomer,OrderDate,true);

        for (ItemInOrderInfo curItem : itemsChosen) {
            if (!curStore.isItemInStore(curItem.serialNumber))
                throw (new StoreDoesNotSellItemException(curItem.serialNumber));
            if (curItem.amountBought <= 0)
                throw (new RuntimeException("Amount is not Allowed.." + curItem.amountBought));

            ProductInStore curProd = curStore.getProductInStoreByID(curItem.serialNumber);
            ProductInOrder newItem = new ProductInOrder(curProd,false);
            newItem.setAmountBought(curItem.amountBought);
            newOrder.addProductToOrder(newItem);
        }

        m_tempOrder = newOrder;

        return getAllEntitledDiscounts(newOrder);
    }


    private void approveOrder() throws OrderIsNotForThisCustomerException {
        for (ProductInOrder curItem :m_tempOrder.getBasket())
            m_ItemsInSystem.get(curItem.getSerialNumber()).addTimesSold(curItem.getAmountByPayingMethod());

        m_OrderHistory.put(m_tempOrder.getOrderSerialNumber(),m_tempOrder);
        updateShippingProfitAfterOrder(m_tempOrder); //update shipping profit
        updateSoldCounterInStore(m_tempOrder); // updated the counter of item in the store (how many times has been sold)
        for (Store curStore : m_tempOrder.getStoreSet())
            curStore.addOrderToStoreHistory(m_tempOrder);
        m_tempOrder.getCostumer().addOrderToHistory(m_tempOrder);

    }

    public OrderInfo getTempOrder() {
        return createOrderInfo(m_tempOrder);
    }

    public OrderInfo getDynamicOrderInfoBeforeDiscounts (Collection<ItemInOrderInfo> itemsChosen,CustomerInfo curUser, Date OrderDate) throws InvalidKeyException, PointOutOfGridException, ItemIsNotSoldAtAllException, CustomerNotInSystemException {
        //NOTE - You need to use approveDynamicOrder to insert to system after
        //part 1 - Create Temp... (UI want to see stores...)
        if (!isCustomerInSystem(curUser.ID))
            throw (new CustomerNotInSystemException(curUser.ID));

        Customer curCustomer = m_CustomersInSystem.get(curUser.ID);

        Store minSellingStore;
        ProductInSystem itemInSys;
        Order newOrder = createEmptyOrder(curCustomer,OrderDate,false);

        for (ItemInOrderInfo curItem : itemsChosen) {
            if (!isItemInSystem(curItem.serialNumber))
                throw new ItemIsNotSoldAtAllException(curItem.serialNumber,"Item is not in system!");
            if (curItem.amountBought <= 0)
                throw (new RuntimeException("Amount is not Allowed.." + curItem.amountBought));

            itemInSys = getItemByID(curItem.serialNumber); //get the item in sys
            minSellingStore = getMinSellingStoreForItem (itemInSys.getSerialNumber()); //get min selling store
            ProductInOrder newItem = new ProductInOrder(minSellingStore.getItemInStore(curItem.serialNumber),false); //create prod in order
            newItem.setAmountBought(curItem.amountBought); //set how much you want
            newOrder.addProductToOrder(newItem); //added it to order
        }

        m_tempOrder = newOrder; //todo wait for sale items...
        return createOrderInfo(newOrder);
    }

    public Collection<DiscountInfo> getDiscountsFromDynamicOrder () {       //create temp static order, return entitled discount..
        //part 2 - get discount - UI want to choose
        if (m_tempOrder.isStatic())
            return null;
        return getAllEntitledDiscounts(m_tempOrder);
    }

    public OrderInfo addDiscounts (Collection<DiscountInfo> DiscountWanted) throws OrderIsNotForThisCustomerException {

        ProductInOrder newItem;
        Store curStore;
        for (DiscountInfo curDiscount : DiscountWanted) {
            if (curDiscount.AmountWanted.getValue() > 0) {
                ProductInStore curProd;
                curStore = getStoreByID(curDiscount.StoreID);
                if (curDiscount.DiscountOperator.toUpperCase().equals("ONE_OF")) {
                    for (int i = 0; i < curDiscount.AmountWanted.getValue(); i++) {
                        int curIndex = curDiscount.getIndex(i);
                        curProd = curStore.getProductInStoreByID(curDiscount.OfferedItem.get(curIndex).ID);
                        newItem = new ProductInOrder(curProd, true);
                        newItem.setAmountBoughtFromSale(curDiscount.OfferedItem.get(curIndex).Amount, curDiscount.OfferedItem.get(curIndex).PricePerOne);
                        m_tempOrder.addProductToOrder(newItem);
                    }
                } else if (curDiscount.DiscountOperator.toUpperCase().equals("ALL_OR_NOTHING")) { //go over all the thing and add them
                    for (int j = 0; j < curDiscount.OfferedItem.size(); j++) {
                        curProd = curStore.getProductInStoreByID(curDiscount.OfferedItem.get(j).ID);
                        newItem = new ProductInOrder(curProd, true);
                        newItem.setAmountBoughtFromSale(
                                curDiscount.AmountWanted.getValue() * curDiscount.OfferedItem.get(j).Amount,
                                curDiscount.OfferedItem.get(j).PricePerOne);
                        m_tempOrder.addProductToOrder(newItem);
                    }
                } else {  //IRRELEVANT
                    curProd = curStore.getProductInStoreByID(curDiscount.OfferedItem.get(0).ID);
                    newItem = new ProductInOrder(curProd, true);
                    newItem.setAmountBoughtFromSale(
                            curDiscount.AmountWanted.getValue() * curDiscount.OfferedItem.get(0).Amount,
                            curDiscount.OfferedItem.get(0).PricePerOne);
                    m_tempOrder.addProductToOrder(newItem);
                }
            }
        }

        return getTempOrder();
    }

    public void ApproveOrder() throws OrderIsNotForThisCustomerException {
        approveOrder();
    }

    private OrderInfo createOrderInfo(Order CurOrder) {
        Set<Store> stores = CurOrder.getStoreSet();
        List<StoreInOrderInfo> storesList = new ArrayList<>();

        Set<ProductInOrder> Items = CurOrder.getBasket();
        List<ItemInOrderInfo> itemsInOrder = new ArrayList<>();

        for (Store curStore : stores)
            storesList.add(new StoreInOrderInfo(getStoreInfoByID(curStore.getStoreID()),
                    CalculateDistance(curStore.getStoreID(),CurOrder.getCoordinate()),
                    CalculatePPK(curStore,CurOrder.getCoordinate()),CurOrder.getPriceFromStore(curStore),
                    CurOrder.getAmountOfItemFromStore(curStore)));

        for (ProductInOrder curProd : Items)
            itemsInOrder.add(new ItemInOrderInfo(curProd.getSerialNumber(),curProd.getProductInStore().getItem().getName(),
                    curProd.getPayBy().toString(),curProd.getProductInStore().getStore().getStoreID(),curProd.getProductInStore().getStore().getName()
                    ,curProd.getAmount(),curProd.getProductInStore().getPricePerUnit(),curProd.getPriceOfTotalItems(),curProd.isFromSale()));

        int ppk =0;
        double distance = 0;
        if (stores.size() == 1)
            for (Store curStore : stores) {
                ppk = curStore.getPPK();
                distance = CalculatePPK(curStore,CurOrder.getCoordinate());
            }

        CustomerInfo UserInOrder = createCustomerInfo(CurOrder.getCostumer());

        return new OrderInfo(CurOrder.getOrderSerialNumber(),CurOrder.getDate(),
                storesList,itemsInOrder,CurOrder.getTotalPrice(),CurOrder.getShippingPrice()
                ,CurOrder.getItemsPrice(),CurOrder.getAmountOfItems(),UserInOrder,CurOrder.isStatic());
    }

    private CustomerInfo createCustomerInfo (Customer user) {
        return new CustomerInfo(user.getName(),user.getId(),user.getCoordinate(),user.getAvgPriceOfShipping(),user.getAvgPriceOfOrdersWithoutShipping(),user.getAmountOFOrders());
    }

    private Order createEmptyOrder (Customer customer, Date OrderDate,boolean isStatic) throws PointOutOfGridException {
        if (!isCoordinateInRange(customer.getCoordinate()))
            throw (new PointOutOfGridException(customer.getCoordinate()));
       // if (m_SystemGrid.containsKey(customer.getCoordinate())) //todo there is now need to check  - xml checkes...
            //throw (new KeyAlreadyExistsException("There is a store at "+ customer.getCoordinate().toString()));

        while (m_OrderHistory.containsKey(OrdersSerialGenerator))
            OrdersSerialGenerator++;

        return new Order (customer,OrdersSerialGenerator++,OrderDate,isStatic);
    }

    public void DeleteItemFromStore(long itemID, long storeID) throws InvalidKeyException, StoreDoesNotSellItemException, ItemIsNotSoldAtAllException, ItemIsTheOnlyOneInStoreException { //bonus

        Store storeByID = getStoreByID(storeID);
        ProductInSystem itemByID = getItemByID(itemID);

        if (!storeByID.isItemInStore(itemID))
            throw new StoreDoesNotSellItemException(storeID);

        if (itemByID.getNumberOfSellingStores() == 1)
            throw new ItemIsNotSoldAtAllException(itemByID.getSerialNumber(), itemByID.getItem().getName());

        if (storeByID.getAmountOfItems() == 1)
            throw new ItemIsTheOnlyOneInStoreException(itemID);

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


    public void UploadInfoFromXML (String XMLPath) throws  NoValidXMLException {
        SuperDuperMarketDescriptor superDuperMarketDescriptor;

        try {
            superDuperMarketDescriptor = FileHandler.UploadFile(XMLPath);

        } catch (JAXBException e) {
            throw new NoValidXMLException();
        }
        currentRunningTask = new LoadXmlTask(superDuperMarketDescriptor,this);
        //controller.bindTaskToUIComponents(currentRunningTask,onFinish); todo this onFinsih
        controller.bindTaskToUIComponents(currentRunningTask);
        new Thread(currentRunningTask).start();
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

    /*void copyInfoFromXMLClasses(SuperDuperMarketDescriptor superDuperMarketDescriptor) throws DuplicateCustomerInSystemException,NegativeQuantityException,IllegalOfferException,NoOffersInDiscountException,DuplicateStoreInSystemException, DuplicateItemIDException, DuplicateItemInStoreException, NegativePriceException, StoreItemNotInSystemException, DuplicatePointOnGridException, StoreDoesNotSellItemException, PointOutOfGridException, ItemIsNotSoldAtAllException, WrongPayingMethodException {

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
    }*/

    void setInfoFromTask (Map<Long,ProductInSystem> ItemsInSystem, Map<Point,Coordinatable> SystemGrid ,Map<Long,Store> StoresInSystem ,
    Map<Long,Order> OrderHistory, Map<Long,Customer> CustomersInSystem) {
        this.m_SystemGrid = SystemGrid;
        this.m_CustomersInSystem = CustomersInSystem;
        this.m_StoresInSystem = StoresInSystem;
        this.m_OrderHistory=OrderHistory;
        this.m_ItemsInSystem=ItemsInSystem;
        locked = false;
    }




    private List<DiscountInfo> getAllEntitledDiscounts(Order order) {

        List<DiscountInfo> res =null;


        if (order.isStatic()) {
            Store staticStore = getStoreByID(order.getStaticStore());
            List<ProductInStore> wantedItemsInStore = new ArrayList<>();
            res = staticStore.getDiscountsListByItems(order.getBasket());
        }
        else { //case dynamic
            res = new ArrayList<>();
            Set<Store> allStores = new HashSet<>();
            for (ProductInOrder curItem : order.getBasket())
                allStores.add(curItem.getProductInStore().getStore());
            for (Store curStore : allStores) {
                res.addAll(curStore.getDiscountsListFilteredByItems(order.getBasket()));
            }
        }

        return res;
    }

    public boolean isItemOkToDelete( ItemInStoreInfo itemSelected) {
        ProductInSystem item = getItemByID(itemSelected.serialNumber);
        return (item.getNumberOfSellingStores() != 1);

    }

    public int getMaxXPoint() {
        List<Integer> maxXPoint = new ArrayList<>();


        for (Coordinatable cur : m_SystemGrid.values()) {
            maxXPoint.add(cur.getCoordinate().x);
        }
        Integer res= Collections.max(maxXPoint);
        if (res != null)
            return res;
        else return 0;
    }

    public int getMaxYPoint() {
        List<Integer> maxYPoint = new ArrayList<>();


        for (Coordinatable cur : m_SystemGrid.values()) {
            maxYPoint.add(cur.getCoordinate().y);
        }
        Integer res= Collections.max(maxYPoint);
        if (res != null)
            return res;
        else
            return 0;
    }

   /*public void test() {
        Order emptyOrder = null;
        Date dt = new Date();

        try {
            emptyOrder = createEmptyOrder(m_CustomersInSystem.get((long)1), dt, true);
        } catch (PointOutOfGridException e) {
            e.printStackTrace();
        }

        ProductInOrder productInOrder = new ProductInOrder(m_StoresInSystem.get((long)1).getProductInStoreByID((long)1),false);
        productInOrder.setAmountBought(5);
        emptyOrder.addProductToOrder(productInOrder);
        productInOrder = new ProductInOrder(m_StoresInSystem.get((long)1).getProductInStoreByID((long)2),false);
        productInOrder.setAmountBought(25);
        emptyOrder.addProductToOrder(productInOrder);
        productInOrder = new ProductInOrder(m_StoresInSystem.get((long)1).getProductInStoreByID((long)5),false);
        productInOrder.setAmountBought(100);
        emptyOrder.addProductToOrder(productInOrder);
        productInOrder = new ProductInOrder(m_StoresInSystem.get((long)1).getProductInStoreByID((long)3),false);
        productInOrder.setAmountBought(1);
        emptyOrder.addProductToOrder(productInOrder);

        m_OrderHistory.put(emptyOrder.getOrderSerialNumber(),emptyOrder); //todo dont add it yet...
        updateShippingProfitAfterOrder(emptyOrder); //update shipping profit
        updateSoldCounterInStore(emptyOrder); // updated the counter of item in the store (how many times has been sold)
        m_StoresInSystem.get((long)1).addOrderToStoreHistory(emptyOrder);
        try {
            m_CustomersInSystem.get((long)1).addOrderToHistory(emptyOrder);
        } catch (OrderIsNotForThisCustomerException e) {
            e.printStackTrace();
        }

        for (ProductInOrder curItem :emptyOrder.getBasket())
            m_ItemsInSystem.get(curItem.getSerialNumber()).addTimesSold(curItem.getAmountByPayingMethod());

    }*/
}
