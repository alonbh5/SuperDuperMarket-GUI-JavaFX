/*package course.java.sdm.console;

import course.java.sdm.engine.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.classesForUI.*;
import sun.dc.path.PathException;
import javax.management.openmbean.InvalidKeyException;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class SDMConsoleUI {

    private SuperDuperMarketSystem MainSDMSystem;
    private Scanner scanner = new Scanner(System.in);
    private ConsoleMenuBuilder MainMenu = new ConsoleMenuBuilder("Super Duper Market");
    private DecimalFormat df = new DecimalFormat();

    public SDMConsoleUI() {
        df.setMaximumFractionDigits(2);
        BuildMainMenu();
        MainSDMSystem = new SuperDuperMarketSystem();
        MainMenu.Show();
    } // C:\Users\alon8\Desktop\ga ga\files1\ex1-big.xml

    private void BuildMainMenu ()    {

        ConsoleMenuBuilder NewOrderMenu = new ConsoleMenuBuilder("Create New Order");
        ConsoleMenuBuilder LoadAndSaveOrderMenu = new ConsoleMenuBuilder("Save/Load Orders XML");
        ConsoleMenuBuilder ChangeItemsMenu = new ConsoleMenuBuilder("Change,Add or Delete Item In Store");

        ChangeItemsMenu.AddMenuItem("Delete Item From Store",this::DeleteItemFromStore);
        ChangeItemsMenu.AddMenuItem("Add new Item To Store",this::AddItemToStore);
        ChangeItemsMenu.AddMenuItem("Change Item's Price From Store",this::ChangeItemPrice);

        NewOrderMenu.AddMenuItem("Static Order",this::StaticOrder);
        NewOrderMenu.AddMenuItem("Dynamic Order",this::DynamicOrder);

        LoadAndSaveOrderMenu.AddMenuItem("Save Orders",this::SaveOrderToFile);
        LoadAndSaveOrderMenu.AddMenuItem("Load Orders",this::LoadOrderToFile);

        MainMenu.AddMenuItem("Upload System XML",this::UploadXML);
        MainMenu.AddMenuItem("View All Stores",this::showAllStore);
        MainMenu.AddMenuItem("View All Items",this::showAllItems);
        MainMenu.AddMenuItem(NewOrderMenu);
        MainMenu.AddMenuItem("Show Orders History",this::showAllOrders);
        MainMenu.AddMenuItem(ChangeItemsMenu);
        MainMenu.AddMenuItem(LoadAndSaveOrderMenu);
    }         //C:\\Users\\alon8\\Desktop\\ga ga\\files1\\
// C:\\Users\\alon8\\Desktop\\ga ga\\files1\\Orders.dat
    //------------------------------------------------------------------------------

    private void UploadXML() {
        String str;
        boolean flag = true;

        while (flag) {
            System.out.println("Please Enter Full Path for XML file (Type Back to return):");
            str = scanner.nextLine();
            if (str.toLowerCase().equals("back"))
                return;
            if (str.toLowerCase().equals("alon"))
                System.out.println("Alon Ben Harosh :)");
            else
            if(checkValidXmlNameEnding(str)) {
                {
                    try {
                        MainSDMSystem.UploadInfoFromXML(str);
                        System.out.println("XML Loaded Successfully!");
                        flag = false;
                    } catch (PointOutOfGridException e) {
                        System.out.println("Error! - Location is not in [0-50]!!");
                        System.out.println(e.getMessage());
                    } catch (NoValidXMLException e) {
                        System.out.println("Error! - XML location was not found - please check path");
                        System.out.println("for exp. C:\\Users\\MyName\\Desktop\\for java\\files\\ex1-big.xml");
                    } catch (DuplicateItemInStoreException e) {
                        System.out.println("Error! - XML contains 2 Items with the same id at one store: " + e.id);
                    } catch (StoreDoesNotSellItemException e) {
                        System.out.println("Error - XML contains Store (" + e.StoreID + ") that does not sell any item");
                    } catch (DuplicateStoreInSystemException e) {
                        System.out.println("Error! - XML contains 2 points at the same location (" + e.Storeid + ").");
                    } catch (DuplicatePointOnGridException e) {
                        System.out.println("Error! - XML contains 2 points at the same location " + e.PointInput + " !!");
                    } catch (ItemIsNotSoldAtAllException e) {
                        System.out.println("Error! -Item #" + e.ItemID + "(" + e.ItemName + ") has no store that sell it");
                    } catch (StoreItemNotInSystemException e) {
                        System.out.println("Error! - Store #" + e.StoreIdInput + " is trying to sell an item that's not in system (Item #" + e.ItemIdInput + ")");
                    } catch (WrongPayingMethodException e) {
                        System.out.println("Error! - Wrong input Paying method - " + e.PayingInput);
                    } catch (DuplicateItemIDException e) {
                        System.out.println("Error! - XML contains 2 Items with the same id : " + e.id);
                    } catch (NegativePriceException e) {
                        System.out.println("Error! - XML contains a Negative Price " + e.PriceReceived + " for Item!");
                    } catch (Exception e) {
                        System.out.println("Unknown Error!");
                    }
                }

            }
            else
                System.out.println("Error - Please Enter Path That's Ends With ..<Path>../<File Name>.xml");
        }
        if (MainSDMSystem.isLocked())
            System.out.println("System is Locked!");
        else
            System.out.println("System Unlocked!");

    } //1

    private void showAllStore () {
        try {
            int i = 1;
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            StringBuilder str = new StringBuilder();
            List<StoreInfo> StoresList = MainSDMSystem.getListOfAllStoresInSystem();
            dateFormat.applyPattern("dd/MM-hh:mm");

            printLineOfStars();
            if (StoresList.isEmpty())
                System.out.println("No Stores In System Yet!");
            else
                for (StoreInfo CurStore : StoresList) {
                    printLineOfEqual();
                    str.append(i++ + ". ");

                    str.append("Store #" + CurStore.StoreID + " \""+ CurStore.Name + "\" \n" +
                            "PPK is: " + CurStore.PPK + ", Location on Grid is ("+CurStore.locationCoordinate.x+","+CurStore.locationCoordinate.y+
                            ")\nSo Far her Profit from Shipping is :" + df.format(CurStore.profitFromShipping) + "\n");

                    if (CurStore.Items.isEmpty())
                        str.append("Does Not Sell Items Yet!\n");
                    else {
                        str.append("Selling Items:\n");
                        for (ItemInStoreInfo curItem : CurStore.Items)
                            str.append(curItem.Name + " #" + curItem.serialNumber + " Paying method is by " + curItem.PayBy.toLowerCase() +
                                    " and it's cost " + df.format(curItem.PriceInStore) + " , Sold " + ((int) curItem.SoldCounter) + " times. \n");
                    }

                    if (CurStore.OrderHistory.isEmpty())
                        str.append("Did Not Sell To Anyone Yet!\n");
                    else {
                        str.append("Order History is:\n");
                        for (OrdersInStoreInfo curOrder : CurStore.OrderHistory)
                            str.append("Order #" + curOrder.OrderSerialNumber +
                                    " At "+dateFormat.format(curOrder.Date) +
                                    " Number of Items: " + curOrder.amountOfItems +
                                    " Cost of only Items: " + df.format(curOrder.ItemsPrice) +
                                    " Cost of Shipping: " + df.format(curOrder.ShippingPrice) +
                                    " Cost of Total Order: " + df.format(curOrder.TotalPrice) + "\n");
                    }

                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        } catch (NoValidXMLException e)
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } //2

    private void showAllItems() {

        try {
            int i = 1;

            StringBuilder str = new StringBuilder();
            List<ItemInfo> itemsList= MainSDMSystem.getListOfAllItems();

            printLineOfStars();
            if (itemsList.isEmpty())
                System.out.println("No Items In System Yet!");
            else
                for (ItemInfo CurItem : itemsList) {
                    printLineOfEqual();
                    str.append(i++ + ". ");
                    str.append(CurItem.Name +", Serial #" + CurItem.serialNumber +", Paying Method is: By " + CurItem.PayBy.toLowerCase()+".\n");
                    str.append("Being Sold in " + CurItem.NumOfSellingStores + " Stores. \n");
                    str.append("Average Price is : " + df.format(CurItem.AvgPrice));
                    str.append("\nWas sold  : " + CurItem.SoldCount + " times.");
                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        } catch (NoValidXMLException e)
        {
            System.out.println("Please Enter a Valid XML before Trying this Options!");
        }catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } //3

    private void StaticOrder() {
        if (MainSDMSystem.isLocked()) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
            return;
        }

        try {
            Date inputDate;
            Point curLocation;

            printSimpleOfAllStores();
            System.out.println("Please Type Store ID from the list above:");
            StoreInfo StoreChosen = checkValidStore();//4.1
            inputDate = getValidDate(); //4.2
            curLocation = getValidPoint(); //4.3
            Collection<ItemInOrderInfo> ItemsChosen = getValidItemsForOrder(StoreChosen); //4.4
            double shipping = MainSDMSystem.CalculatePPK(StoreChosen.StoreID,curLocation);
            int ppk =MainSDMSystem.getPPK(StoreChosen.StoreID);
            double distance = MainSDMSystem.CalculateDistance(StoreChosen.StoreID,curLocation);
            if (approveOrder(ItemsChosen,shipping,ppk,distance))//4.5
            {
                MainSDMSystem.addStaticOrderToSystem(ItemsChosen,StoreChosen,curLocation,inputDate); //leahed many item to one!
                System.out.println("Order Added To System!");
            }
            else
                System.out.println("Order Was Canceled!");


        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (StoreDoesNotSellItemException e) {
            System.out.println("There was a problem - the store does not sell : "+e.StoreID);
        } catch (PointOutOfGridException e) {
            System.out.println("Current location is not on grid [0-50] "+e.PointReceived);
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } //4-1

    private void DynamicOrder() { //bonus
        if (MainSDMSystem.isLocked())
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
            return;
        }

        try {
            Date inputDate;
            Point curLocation;

            inputDate = getValidDate(); //4.2
            curLocation = getValidPoint(); //4.3
            Collection<ItemInOrderInfo> ItemsChosen = getValidItemsForOrder(); //4.4
            if (approveDynamicOrder(MainSDMSystem.addDynamicOrderToSystem(ItemsChosen,curLocation,inputDate)))//4.5
            {
                MainSDMSystem.approveDynamicOrder();
                System.out.println("Order Added To System!");
            }
            else
                System.out.println("Order Was Canceled!");

        } catch (ItemIsNotSoldAtAllException e) {
            System.out.println("Item "+e.ItemName +" is not in System");
        } catch (PointOutOfGridException e) {
            System.out.println("Point is not in Grid");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } //4-2 bonus

    private void showAllOrders() {
        try {
            int i = 1;
            StringBuilder str = new StringBuilder();
            List<OrderInfo> OrderList= MainSDMSystem.getListOfAllOrderInSystem();
            SimpleDateFormat dateFormat = new SimpleDateFormat();

            dateFormat.applyPattern("dd/MM-hh:mm");
            printLineOfStars();
            if (OrderList.isEmpty())
                System.out.println("No Orders In System Yet!");
            else
                for (OrderInfo CurOrder : OrderList) {
                    str.append(i++ + ". ");
                    str.append("Order #" + CurOrder.m_OrderSerialNumber + " at ");
                    str.append(dateFormat.format(CurOrder.m_Date));
                    if (CurOrder.Stores.size() == 1)
                        str.append(" From "+ CurOrder.Stores.get(0));
                    else {
                        int j = 1;
                        str.append("\nStores in Order are :\n");
                        for (String curStore : CurOrder.Stores)
                            str.append(j++ +") "+ curStore + ".\n");
                    }

                    str.append("\nThe Number of Items in Order: " + CurOrder.m_amountOfItems +
                            "\n-Cost of only Items: " + df.format(CurOrder.m_ItemsPrice) +
                            "\n-Cost of Shipping: " + df.format(CurOrder.m_ShippingPrice) +
                            "\n-Cost of Total Order: " + df.format(CurOrder.m_TotalPrice));
                    printLineOfStars();
                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        }
        catch (NoValidXMLException e)
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    }   //5

    private void ChangeItemPrice()  { //bonus
        try {
            printSimpleOfAllStores();
            System.out.println("Please Type Store ID from the list above for which you wish to Change Price of an item from:");
            StoreInfo StoreChosen = checkValidStore();
            printAllItemsFromStore(StoreChosen);
            System.out.println("Please Type Item ID from the list above you wish to Change Price:");
            long ItemID = getValidItemFromStore(StoreChosen);
            System.out.println("Please Enter your new Price:");
            double newPrice = getPosPrice();
            MainSDMSystem.ChangePrice(ItemID,StoreChosen.StoreID,newPrice);
            System.out.println("Item Price has been Changed!");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        }  catch (StoreDoesNotSellItemException e) {
            System.out.println("Sorry! This is the only Store that sell this Item - Deletion is not possible.");
        } catch (NegativePriceException e) {
            System.out.println("Error - Negative Price Received by System!");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } // 6-1 bonus

    private void DeleteItemFromStore() { //bonus
        try {
            printSimpleOfAllStores();
            System.out.println("Please Type Store ID from the list above for which you wish to delete an item from:");
            StoreInfo StoreChosen = checkValidStore();
            printAllItemsFromStore(StoreChosen);
            System.out.println("Please Type Item ID from the list above you wish to delete from Store:");
            long ItemID = getValidItemFromStore(StoreChosen);
            MainSDMSystem.DeleteItemFromStore(ItemID,StoreChosen.StoreID);
            System.out.println("Item has been deleted!");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (ItemIsNotSoldAtAllException e) {
            System.out.println("This Item is not being sold at this store!.");
        } catch (StoreDoesNotSellItemException e) {
            System.out.println("Sorry! This is the only Store that sell this Item - Deletion is not possible.");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }

    } //6-2 bonus

    private void AddItemToStore() { //bonus
        try {
            printSimpleOfAllStores();
            System.out.println("Please Type Store ID from the list above for which you wish to add an Item To:");
            StoreInfo StoreChosen = checkValidStore();
            printAllItemsWithFilterOfStore(StoreChosen);
            System.out.println("Please Type Item ID from the list above that not being sold at store and you want to add it to store:");
            long ItemID = getItemNotInStore(StoreChosen);
            System.out.println("Please Enter Price for item:");
            double price =getPosPrice();
            MainSDMSystem.addItemToStore(StoreChosen.StoreID,new ItemInStoreInfo(ItemID,price));
            System.out.println("New Item Added To System!");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (NegativePriceException e) {
            System.out.println("Error - Negtice Price "+e.PriceReceived);
        } catch (DuplicateItemInStoreException e) {
            System.out.println("This Item is Already in Store - if You Want Go back and Choose the 'Change Price' Option");
        } catch (StoreItemNotInSystemException e) {
            System.out.println("Error - the item you picked is not in System");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    } //6-3 bonus

    private void SaveOrderToFile() {
        if (MainSDMSystem.isLocked()) {

            System.out.println("Please Upload a Valid XML before Trying this Options!");
            return;
        }
        if (MainSDMSystem.getAmountOfOrdersInSystem() == 0) {
            System.out.println("Sorry! it's seem there is no Orders in System Yet!");
            return;
        }

        System.out.println("Please Enter Full Path You Wish To Save Order (XML file will be created there)");
        String strPath = scanner.nextLine();
        try {
            MainSDMSystem.SaveOrdersToBin(strPath);
            System.out.println("Orders Were Save Successfully to Path: "+strPath + "\\Orders.dat!");
        } catch (IOException e) {
            System.out.println("Error Creating File!");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (PathException e) {
            System.out.println(e.getMessage());
            System.out.println("Path for exp. C:\\Users\\MyUser\\Desktop\\for java\\files\\");
        } catch (Exception e) {
            System.out.println("Unknown Error!");
            System.out.println("Path for exp. C:\\Users\\MyUser\\Desktop\\for java\\files\\");
        }

    } //7-1 bonus

    private void LoadOrderToFile() {
        if (MainSDMSystem.isLocked()) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
            return;
        }
        System.out.println("Please Enter Full Path of Order File. NOTE - Orders Will Be Added To System And Will Not Overwrite Them");
        String strPath = scanner.nextLine();
        try {
            MainSDMSystem.LoadOrderFromFile(strPath);
            System.out.println("Orders Added To System!!");
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        } catch (IOException e) {
            System.out.println("Error! - File Was Not Found!");
            System.out.println("Path for exp. C:\\Users\\MyUser\\Desktop\\for java\\files\\Orders.dat");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry! The File Seems To Be Corrupted!");
        } catch (NegativePriceException e) {
            System.out.println("File Contained Negative Price in Order");
        } catch (PathException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }

    } //7-2 bonus

    //------------------------------------------------------------------------------

    private double getPosPrice() {
        boolean flag= true;
        double res = 0;
        String str;
        while (flag)
        {
            try {
                str = scanner.nextLine();
                res = Double.parseDouble(str);
                if (res <= 0)
                    System.out.println("Please Enter a Positive Number");
                else
                    flag =false;
            }catch (Exception e) {
                System.out.println("Please enter a number!");
            }}
        return res;
    }

    private Point getValidPoint() {
        Point res = new Point(-1,-1);
        boolean flag = true;
        int x=-1,y=-1;

        while (flag) {
            System.out.println("Please Enter Current Location On Grid (between "+SuperDuperMarketSystem.MIN_COORDINATE+"-"
                    +SuperDuperMarketSystem.MAX_COORDINATE +" for both X and Y)");
            System.out.print("For X: ");
            String xStr = scanner.nextLine();
            System.out.print("For Y: ");
            String yStr = scanner.nextLine();
            try {
                x = Integer.parseInt(xStr);
                y = Integer.parseInt(yStr);} catch (NumberFormatException e) {
                System.out.println("Wrong Input - try again");
            }
            res = new Point(x,y);
            if (!SuperDuperMarketSystem.isCoordinateInRange(res))
                System.out.println("ERROR - Please Enter Current Location On Grid (between "+SuperDuperMarketSystem.MIN_COORDINATE+"-"
                        +SuperDuperMarketSystem.MAX_COORDINATE +" for both X and Y)");
            else
            if (MainSDMSystem.isLocationTaken(res))
                System.out.println("There is a Store at this Location - try Again");
            else
                flag = false;
        }
        return res;
    }

    private Date getValidDate() {
        Date res= Date.from(Instant.now());
        boolean flag=true;
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        dateFormat.applyPattern("dd/MM-hh:mm");

        while (flag) {

            System.out.println("Please Type Order Date in the form of dd/mm-hh:mm (E.g 02/11-13:56):");
            String inputDateString = scanner.nextLine();
            try {
                dateFormat.parse(inputDateString);
                if(isDateOk(inputDateString)) {
                    res = dateFormat.parse(inputDateString);
                    flag = false;
                }
                else
                    System.out.println("Error - Remember Time limits , dd (00-31) MM(01-12) hh(00-23) mm(00-59)");
            } catch (ParseException e) {
                System.out.println("Date Entered is not in the Form dd/MM-hh:mm");
                System.out.println("Remember Time limits , dd (00-31) MM(01-12) hh(00-23) mm(00-59)");
            } catch (Exception e) {
                System.out.println("Remember Time limits , dd (00-31) MM(01-12) hh(00-23) mm(00-59)");
            } //4.2
        }

        return res;
    }

    private long getValidIDNumber () {
        boolean flag= true;
        long res = 0;
        String str = null;
        while (flag)
        {
            try {
                str = scanner.nextLine();
                res = Long.parseLong(str);
                if (res <= 0)
                    System.out.println("Please Enter a Positive Number");
                else
                    flag =false;
            }catch (Exception e) {
                System.out.println("Please enter a number!");
            }}
        return res;
    }

    private Collection<ItemInOrderInfo> getValidItemsForOrder(StoreInfo storeChosen) {

        long ItemID;
        double amountWanted = 0;
        Map<Long,ItemInOrderInfo> Basket = new HashMap<>();
        boolean flag = true;
        try {

            printLineOfEqual();
            printAllItemsWithFilterOfStore(storeChosen);
            printLineOfEqual();

        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!"); //can ignore here..
        }


        while (flag) {
            System.out.println("Please Enter Item ID you Want to add to Basket, Enter q to Quit");
            String str = scanner.nextLine();
            if (str.equals("q"))
                flag = false;
            else {
                try{
                    ItemID = Long.parseLong(str);
                    if (!MainSDMSystem.isItemSoldInStore(storeChosen.StoreID, ItemID))
                        System.out.println("Item #"+ItemID + " is not sold in "+storeChosen.Name);
                    else{
                        ItemInfo wantedItem = MainSDMSystem.getItemInfo(ItemID);
                        if (wantedItem.PayBy.toLowerCase().equals("amount"))
                        {
                            System.out.println("Please Enter How Many "+wantedItem.Name+"'s you want");
                            amountWanted = (double)getValidIDNumber();
                        }
                        else
                        {
                            System.out.println("Please Enter How Many Kg of " +wantedItem.Name+" You Want (dec. is possible)");
                            boolean end = false;
                            while (!end) {
                                try {
                                    str=scanner.nextLine();
                                    amountWanted = Double.parseDouble(str);
                                    if (amountWanted <= 0)
                                        System.out.println("Please Enter a Positive Number");
                                    else
                                        end = true;
                                }catch (InputMismatchException e) {
                                    System.out.println("Not a Number! - try again");
                                }
                            }
                        }


                        if (Basket.containsKey(wantedItem.serialNumber)) {
                            System.out.println("Already in Basket - Adding "+ amountWanted);
                            Basket.get(wantedItem.serialNumber).amountBought += amountWanted;
                        }
                        else
                        {
                            double itemPrice = MainSDMSystem.getItemPriceInStore(storeChosen.StoreID, wantedItem.serialNumber);
                            Basket.put(wantedItem.serialNumber,new ItemInOrderInfo(wantedItem.serialNumber,
                                    wantedItem.Name,wantedItem.PayBy,storeChosen.StoreID,
                                    amountWanted,itemPrice,itemPrice*amountWanted));
                            System.out.println("Added "+ wantedItem.Name+" To Basket!");
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong Input- Try Again!");
                }
            }
        }
        return Basket.values();
    }

    private Collection<ItemInOrderInfo> getValidItemsForOrder() throws NoValidXMLException {

        long ItemID;
        double amountWanted = 0;
        Map<Long,ItemInOrderInfo> Basket = new HashMap<>();
        boolean flag = true;

        printLineOfEqual();
        printAllItemsSimple();
        printLineOfEqual();

        while (flag) {
            System.out.println("Please Enter Item ID you Want to add to Basket, Enter q to Quit");
            String str = scanner.nextLine();
            if (str.equals("q"))
                flag = false;
            else {
                try{
                    ItemID = Long.parseLong(str);
                    if (!MainSDMSystem.isItemInSystem(ItemID))
                        System.out.println("Item #"+ItemID + " is not sold in system!");
                    else{
                        ItemInfo wantedItem = MainSDMSystem.getItemInfo(ItemID);
                        if (wantedItem.PayBy.toLowerCase().equals("amount"))
                        {
                            System.out.println("Please Enter How Many "+wantedItem.Name+"'s you want");
                            amountWanted = (double)getValidIDNumber();
                        }
                        else
                        {
                            System.out.println("Please Enter How Many Kg of " +wantedItem.Name+" You Want (dec. is possible)");
                            amountWanted = getPosPrice();
                        }
                        if (Basket.containsKey(wantedItem.serialNumber)) {
                            System.out.println("Already in Basket - Adding "+ amountWanted);
                            Basket.get(wantedItem.serialNumber).amountBought += amountWanted;
                        }
                        else
                        {
                            Basket.put(wantedItem.serialNumber,new ItemInOrderInfo(wantedItem.serialNumber, amountWanted));
                            System.out.println("Added "+ wantedItem.Name+" To Basket!");
                        }

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong Input- Try Again!");
                }
            }
        }
        return Basket.values();
    }

    private long getItemNotInStore(StoreInfo storeChosen) {
        boolean flag = true;
        long res = -1;

        while (flag) {
            res = getValidIDNumber();
            if (storeChosen.isItemIDinStore(res))
                System.out.println("Please Choose item ID from the list above that's not sold in store!");
            else
            if(MainSDMSystem.isItemInSystem(res))
                flag=false;
            else
                System.out.println("Please Choose item ID from the list above! (The Number after #xxxxxxx)");
        }
        return res;
    }

    private long getValidItemFromStore(StoreInfo storeChosen) {

        boolean flag = true;
        long res = -1;

        while (flag) {
            res = getValidIDNumber();
            if (storeChosen.isItemIDinStore(res))
                flag = false;
            else
                System.out.println("Please Choose item ID from the list above! (The Number after #xxxxxxx)");
        }
        return res;
    }


    private void printSimpleOfAllStores () throws NoValidXMLException {
        List<StoreInfo> listOfAllStoresInSystem = MainSDMSystem.getListOfAllStoresInSystem();

        if (listOfAllStoresInSystem.isEmpty()) {
            System.out.println("Cant Use This Option - No Store in System Yet");
            return;
        }

        for (StoreInfo curStore : listOfAllStoresInSystem)
            System.out.println("store #"+curStore.StoreID+ " - "+curStore.Name+" PPK is "+curStore.PPK);

    }

    private void printAllItemsWithFilterOfStore(StoreInfo storeChosen) throws NoValidXMLException {
        List<ItemInfo> allItem = MainSDMSystem.getListOfAllItems();

        for (ItemInfo curItem : allItem) {
            System.out.print(String.format("%d - %s sold by %s", curItem.serialNumber, curItem.Name, curItem.PayBy));
            if (MainSDMSystem.isItemSoldInStore(storeChosen.StoreID, curItem.serialNumber))
                System.out.println(" Price in Store - " + MainSDMSystem.getItemPriceInStore(storeChosen.StoreID, curItem.serialNumber));
            else
                System.out.println(" Item is Not Being Sold In Store");
        }
    }

    private void printAllItemsSimple() throws NoValidXMLException {
        List<ItemInfo> allItem = MainSDMSystem.getListOfAllItems();

        for (ItemInfo curItem : allItem)
            System.out.println(String.format("%d - %s sold by %s", curItem.serialNumber, curItem.Name, curItem.PayBy));
    }

    private void printAllItemsFromStore (StoreInfo storeToShow)  {
        int i=1;
        for (ItemInStoreInfo curItem : storeToShow.Items)
            System.out.println(i++ +". "+curItem.Name + " ID #"+curItem.serialNumber+" Cost "+df.format(curItem.PriceInStore)+".");
    }

    private static void printLineOfStars () {System.out.println("***************************************************************************************");
    }

    private static void printLineOfEqual () {System.out.println("=======================================================================================");
    }


    private boolean checkValidXmlNameEnding (String str) {
        int len = str.length();
        if (len < 4)
            return false;
        String strEnding = str.substring(len - 4 ).toLowerCase();
        return (strEnding.equals(".xml"));
    }

    private StoreInfo checkValidStore() {
        boolean flag = true;

        while (flag) {
            long input = getValidIDNumber();
            try {
                StoreInfo storeInfo = MainSDMSystem.getStoreInfoByID(input);
                return storeInfo;
            } catch (InvalidKeyException e) {
                System.out.println("Error - Please choose Store ID by the list above!");
            }
        }
        return null;
    }

    private boolean isDateOk(String inputDateString) { //only when checked..

        if (inputDateString.length() != "dd/MM-hh:mm".length())
            return false;

        char[] strArray = inputDateString.toCharArray();

        if (strArray[0] >= '4')
            return false;
        if (strArray[0] == '3' && strArray[1] >= '2') //biggest dd 31
            return false;
        if (strArray[3] >= '2')
            return false;
        if (strArray[3] == '1' && strArray[4] >= '3') //biggest mm = 12
            return false;
        if (strArray[6] >= '3')
            return false;
        if (strArray[6] == '2' && strArray[7] >= '5') //biggest hh 24 == 00
            return false;
        if (strArray[9] >= '6')
            return false;

        return true;
    }

    private boolean approveOrder(Collection<ItemInOrderInfo> itemsChosen,double shipping,int PPK,double distance) {
        if (itemsChosen.isEmpty())
            return false;

        printLineOfStars();
        for (ItemInOrderInfo curItem : itemsChosen)
            System.out.println("Item #"+curItem.serialNumber+" ("+curItem.Name+") Pay by "+curItem.PayBy+"," +
                    " Price per Unit is "+curItem.PricePerUint+" X "+curItem.amountBought+"," +
                    " Total Cost = "+(curItem.amountBought*curItem.PricePerUint)+".");


        System.out.println("Distance From Store "+df.format(distance)+" and ppk is "+PPK);
        System.out.println(String.format("Shipping will Cost you %.2f",shipping));
        printLineOfStars();

        System.out.println("Type Yes to Complete Order, or Anything Else To Cancel ");
        String ans =  scanner.nextLine();
        if (ans.toLowerCase().equals("yes"))
            return true;
        return false;
    }

    private boolean approveDynamicOrder(OrderInfo DynamicOrder) {

        printLineOfStars();
        for (ItemInOrderInfo curItem : DynamicOrder.ItemsInOrder)
            System.out.println("Item #"+curItem.serialNumber+" ("+curItem.Name+") Pay by "+curItem.PayBy+"," +
                    "From Store #"+curItem.FromStoreID+ " Price per Unit is "+curItem.PricePerUint+" Amount is "+curItem.amountBought+"," +
                    " Total Cost "+curItem.TotalPrice+".");


        System.out.println(String.format("Shipping will Cost you %.2f",DynamicOrder.m_ShippingPrice));
        System.out.println(String.format("Total is %.2f",DynamicOrder.m_TotalPrice));
        printLineOfStars();

        System.out.println("Type Yes to Complete Order, or Anything Else To Cancel ");
        String ans =  scanner.nextLine();
        if (ans.toLowerCase().equals("yes"))
            return true;
        return false;
    }
}*/