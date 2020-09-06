package course.java.sdm.engine;
import course.java.sdm.exceptions.*;
import course.java.sdm.generatedClasses.*;
import javafx.concurrent.Task;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoadXmlTask extends Task<Boolean> {

    private final SuperDuperMarketDescriptor superDuperMarketDescriptor;
    private final SuperDuperMarketSystem MainSys;

    Map<Long,ProductInSystem> ItemsInSystem = new HashMap<>();
    Map<Point,Coordinatable> SystemGrid = new HashMap<>();
    Map<Long,Store> StoresInSystem = new HashMap<>();
    Map<Long,Order> OrderHistory = new HashMap<>();
    Map<Long,Customer> CustomersInSystem = new HashMap<>();

    private final int SLEEP_TIME = 1; //todo change this before submit

     LoadXmlTask(SuperDuperMarketDescriptor superDuperMarketDescriptor, SuperDuperMarketSystem mainSys) {
        this.superDuperMarketDescriptor = superDuperMarketDescriptor;
        MainSys = mainSys;
         exceptionProperty().addListener((observable, oldValue, newValue) ->  {
             if(newValue != null) {
                 updateProgress(0,0);
             }
         });
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage("Preparing...");
        return copyInfoFromXMLClasses(superDuperMarketDescriptor);
    }

    private boolean copyInfoFromXMLClasses(SuperDuperMarketDescriptor superDuperMarketDescriptor) throws Exception { //todo what if there is no customers or somthing

            Thread.sleep(SLEEP_TIME);
            updateMessage("Getting Items...");
            updateProgress(0,100);

            for (SDMItem Item : superDuperMarketDescriptor.getSDMItems().getSDMItem()) {
                if (!ItemsInSystem.containsKey((long)Item.getId()))
                    crateNewItemInSystem(Item);
                else {
                    updateMessage("Error! - XML contains 2 Items with the same id : " + Item.getId());
                    throw new DuplicateItemIDException(Item.getId());
                }
            }
            Thread.sleep(SLEEP_TIME);
            updateProgress(30,100);
            updateMessage("Getting Stores..");

            for (SDMStore Store : superDuperMarketDescriptor.getSDMStores().getSDMStore()) {
                if (!StoresInSystem.containsKey((long)Store.getId()))
                    crateNewStoreInSystem(Store);
                else {
                    updateMessage("Error! - XML contains 2 Stores at the same location" + Store.getId());
                    throw new DuplicateStoreInSystemException(Store.getId());
                }
            }

            Thread.sleep(SLEEP_TIME);
            updateProgress(60,100);
            updateMessage("Getting Customers..");

            for (SDMCustomer customer : superDuperMarketDescriptor.getSDMCustomers().getSDMCustomer()) {
                if (!CustomersInSystem.containsKey((long)customer.getId()))
                    crateNewCustomerInSystem(customer);
                else {
                    updateMessage("Error! - XML contains 2 Customers at the same location" + customer.getId());
                    throw new DuplicateCustomerInSystemException(customer.getId());
                }
            }

            Thread.sleep(SLEEP_TIME);
            updateProgress(90,100);
            updateMessage("Validating System Information");

            checkMissingItem();
            MainSys.setInfoFromTask(ItemsInSystem,SystemGrid,StoresInSystem,OrderHistory,CustomersInSystem);
            updateProgress(100,100);
            updateMessage("XML Loaded Successfully! - System Unlocked!");
            return true;
    }

    private void crateNewItemInSystem(SDMItem item) throws WrongPayingMethodException {
            Item.payByMethod ePayBy;

            if (item.getPurchaseCategory().equals("Weight"))
                ePayBy = Item.payByMethod.WEIGHT;
            else
            if (item.getPurchaseCategory().equals("Quantity"))
                ePayBy = Item.payByMethod.AMOUNT;
            else {
                updateMessage("Error! - Wrong Input Paying method - " + item.getPurchaseCategory());
                throw new WrongPayingMethodException(item.getPurchaseCategory());
            }

            Item newBaseItem = new Item ((long)item.getId(),item.getName(),ePayBy);
            ProductInSystem newItem = new ProductInSystem(newBaseItem);
            ItemsInSystem.put(newItem.getSerialNumber(),newItem);
        }


    private void crateNewStoreInSystem(SDMStore store) throws PointOutOfGridException, DuplicatePointOnGridException, NegativePriceException, StoreItemNotInSystemException, DuplicateItemInStoreException, StoreDoesNotSellItemException, IllegalOfferException, NegativeQuantityException, NoOffersInDiscountException {
        Point StoreLocation = new Point(store.getLocation().getX(), store.getLocation().getY());
        if (!SuperDuperMarketSystem.isCoordinateInRange(StoreLocation)) {
            updateMessage("Error! - Location is not in at Range - " + StoreLocation.getX() + ","+ StoreLocation.getY()+" !");
            throw new PointOutOfGridException(StoreLocation);}
        if (SystemGrid.containsKey(StoreLocation)) {
            updateMessage("Error! - XML contains 2 points at the same location " + StoreLocation.getX() + ","+ StoreLocation.getY()+" !");
            throw new DuplicatePointOnGridException(StoreLocation);}

        Store newStore = new Store((long) store.getId(), StoreLocation, store.getName(), store.getDeliveryPpk());
        ProductInSystem sysItem;

        for (SDMSell curItem : store.getSDMPrices().getSDMSell()) {
            Long ItemID = (long) curItem.getItemId();
            double itemPrice = curItem.getPrice();

            if (itemPrice <= 0) {
                updateMessage("Error! - XML contains a Negative Price " + itemPrice + " for Item!");
                throw new NegativePriceException(itemPrice);
            }
            if (!ItemsInSystem.containsKey(ItemID)) {
                updateMessage("Error! - Store #" + store.getId() + " is trying to sell an item that's not in system (Item #" + ItemID + ")");
                throw new StoreItemNotInSystemException(ItemID, newStore.getStoreID());
            }
            if (newStore.isItemInStore(ItemID)) {
                updateMessage("Error! - XML contains 2 Items with the same id at Store #" + store.getId());
                throw new DuplicateItemInStoreException(ItemID);
            }

            Item BaseItem = ItemsInSystem.get(ItemID).getItem();
            ProductInStore newItemForStore = new ProductInStore(BaseItem, itemPrice, newStore);
            newStore.addItemToStore(newItemForStore);
            sysItem = ItemsInSystem.get(ItemID);
            sysItem.addSellingStore();
            if (sysItem.getMinSellingStore() == null || itemPrice < sysItem.getMinSellingStore().getPriceForItem(BaseItem.getSerialNumber()))
                sysItem.setMinSellingStore(newStore);

        }
//todo check spaces and case sensetuve  "    sdas "  = "sdas"
        if (newStore.getItemList().isEmpty()) {
            updateMessage("Error! - Store #"+store.getId() + "Does not Sell any item!");
            throw new StoreDoesNotSellItemException(newStore.getStoreID());
        }

        if (store.getSDMDiscounts() != null)
            for (SDMDiscount curDis : store.getSDMDiscounts().getSDMDiscount()) {
                if (!newStore.isItemInStore((long) curDis.getIfYouBuy().getItemId())) {
                    updateMessage("Error! -Item #"+curDis.getIfYouBuy().getItemId()+" from Discount "+curDis.getName()+" is not sold at store");
                    throw new StoreDoesNotSellItemException("Item of Discount is not sold at store", curDis.getIfYouBuy().getItemId());
                }
                if (curDis.getIfYouBuy().getQuantity() < 0) {
                    updateMessage("Error! -Discount "+curDis.getName()+" want you to Buy Negative Amount...");
                    throw new NegativeQuantityException(curDis.getIfYouBuy().getQuantity());
                }
                if (curDis.getThenYouGet().getSDMOffer().isEmpty()) {
                    updateMessage("Error! -Discount "+curDis.getName()+" does not offer anything...");
                    throw new NoOffersInDiscountException(curDis.getName());
                }

                Discount.OfferType newOp = Discount.OfferType.IRRELEVANT;
                if (curDis.getThenYouGet().getOperator().equals("ONE-OF"))
                    newOp = Discount.OfferType.ONE_OF;
                if (curDis.getThenYouGet().getOperator().equals("ALL-OR-NOTHING"))
                    newOp = Discount.OfferType.ALL_OR_NOTHING;
                if (newOp.equals(Discount.OfferType.IRRELEVANT) && curDis.getThenYouGet().getSDMOffer().size() != 1) {
                    updateMessage("Error! -Discount "+curDis.getName()+" Say it IRRELEVANT what you choose but offer more then one product!");
                    throw new IllegalOfferException(curDis.getName());}

                Item curItem = ItemsInSystem.get((long) curDis.getIfYouBuy().getItemId()).getItem();
                ProductYouBuy whatYouBuy = new ProductYouBuy(curItem, curDis.getIfYouBuy().getQuantity());
                Discount newDis = new Discount(newOp, curDis.getName(), whatYouBuy);

                for (SDMOffer offer : curDis.getThenYouGet().getSDMOffer()) {
                    if (offer.getForAdditional() < 0) {
                        updateMessage("Error! -Discount "+curDis.getName()+" Wants to give you money!");
                        throw new NegativePriceException(offer.getForAdditional());}
                    if (!newStore.isItemInStore((long) offer.getItemId())) {
                        updateMessage("Error - Item #" +offer.getItemId()+" of Discount is not sold at store");
                        throw new StoreDoesNotSellItemException("Item of Discount is not sold at store", curDis.getIfYouBuy().getItemId());}
                    if (offer.getQuantity() < 0) {
                        updateMessage("Error! -Discount "+curDis.getName()+" Quantity is Negative");
                        throw new NegativeQuantityException(offer.getQuantity());}

                    Item itemForCtor = ItemsInSystem.get((long) offer.getItemId()).getItem();

                    newDis.AddProductYouGet(new ProductYouGet(itemForCtor,new Double(offer.getQuantity()), new Double(offer.getForAdditional())));
                }
                newStore.addDiscount(newDis);
            }

        StoresInSystem.put(newStore.getStoreID(), newStore);
        SystemGrid.put(newStore.getCoordinate(), newStore);
    }

    private void crateNewCustomerInSystem(SDMCustomer customer) throws PointOutOfGridException, DuplicatePointOnGridException {

        Point location = new Point(customer.getLocation().getX(),customer.getLocation().getY());

        if (!SuperDuperMarketSystem.isCoordinateInRange(location)) {
            updateMessage("Error! - Location is not in at Range - " + location.getX() + ","+ location.getY()+" !");
            throw new PointOutOfGridException(location);
        }
        if (SystemGrid.containsKey(location)) {
            updateMessage("Error! - XML contains 2 points at the same location " + location.getX() + ","+ location.getY()+" !");
            throw new DuplicatePointOnGridException(location);}
        Customer newUser = new Customer(customer.getId(),customer.getName(),location);
        CustomersInSystem.put(newUser.getIdNumber(),newUser);
        SystemGrid.put(newUser.getCoordinate(),newUser);
    }


    private void checkMissingItem() throws ItemIsNotSoldAtAllException {
        for (ProductInSystem curItem : ItemsInSystem.values())
            if (curItem.getNumberOfSellingStores() == 0) {
                updateMessage("Error! -Item #" + curItem.getSerialNumber() + "(" + curItem.getItem().getName() + ") has no store that sell it");
                throw new ItemIsNotSoldAtAllException(curItem.getSerialNumber(), curItem.getItem().getName());
            }
    }

}
