package course.java.sdm.console;

import course.java.sdm.engine.*;
import course.java.sdm.exceptions.*;
import course.java.sdm.classesForUI.*;

import javax.management.openmbean.InvalidKeyException;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class SDMConsoleUI {

    //todo check all scanner
    //todo check all exceptions is checked
    //todo scanner is only nextline

    SuperDuperMarketSystem MainSDMSystem;
    Scanner scanner = new Scanner(System.in);
    ConsoleMenuBuilder MainMenu = new ConsoleMenuBuilder("Super Duper Market");

    public SDMConsoleUI() {
        BuildMainMenu();
        MainSDMSystem = new SuperDuperMarketSystem();
        MainMenu.Show();
    }

    private void BuildMainMenu ()
    {
        ConsoleMenuBuilder NewOrderMenu = new ConsoleMenuBuilder("Create New Order");
        ConsoleMenuBuilder SaveOrderMenu = new ConsoleMenuBuilder("Save Orders To XML");
        ConsoleMenuBuilder LoadOrderMenu = new ConsoleMenuBuilder("Load Orders XML");
        ConsoleMenuBuilder ChangeItemsMenu = new ConsoleMenuBuilder("Change,Add or Delete Item In Store");

        ChangeItemsMenu.AddMenuItem("Delete Item From Store",this::DeleteItemFromStore);
        ChangeItemsMenu.AddMenuItem("Add new Item To Store",this::AddItemToStore);
        ChangeItemsMenu.AddMenuItem("Change Item's Price From Store",this::ChangeItemPrice);

        NewOrderMenu.AddMenuItem("Static Order",this::StaticOrder);
        NewOrderMenu.AddMenuItem("Dynamic Order",this::DynamicOrder);
        
        
        MainMenu.AddMenuItem("Upload System XML",this::UploadXML);
        MainMenu.AddMenuItem("View All Stores",this::showAllStore);
        MainMenu.AddMenuItem("View All Items",this::showAllItems);
        MainMenu.AddMenuItem(NewOrderMenu);
        MainMenu.AddMenuItem("Show Orders History",this::showAllOrders);
        MainMenu.AddMenuItem(ChangeItemsMenu);
        MainMenu.AddMenuItem(SaveOrderMenu);
        MainMenu.AddMenuItem(LoadOrderMenu);
    }

    private void UploadXML() {
        String str;
        boolean flag = true;

        while (flag) {
            System.out.println("Please Enter Full Path for XML file:");
            str = scanner.next();
            if(checkValidXmlNameEnding(str)) {
                //str = "/files1/ex1-big.xml";
                str = "/files1/ex1-error-3.6.xml";
                try {
                    MainSDMSystem.UploadInfoFromXML(str);
                    flag = false;
                } catch (PointOutOfGridException e) {
                    System.out.println("Error! - Location is not in [0-50]!!");
                    System.out.println(e.getMessage());
                } catch (NoValidXMLException e) {
                    System.out.println("Error! - XML location was not found - please check path");
                } catch (DuplicateItemInStoreException e) {
                    System.out.println("Error! - XML contains 2 Items with the same id at one store: "+e.id);
                } catch (StoreDoesNotSellItemException e) {
                    System.out.println("Error - XML contains Store ("+e.StoreID+") that does not sell any item");
                } catch (DuplicateStoreInSystemException e) {
                    System.out.println("Error! - XML contains 2 points at the same location ("+e.Storeid+").");
                } catch (DuplicatePointOnGridException e) {
                    System.out.println("Error! - XML contains 2 points at the same location "+e.PointInput+" !!");
                } catch (ItemIsNotSoldAtAllException e) {
                    System.out.println("Error! -Item #"+e.ItemID + "("+e.ItemName+") has no store that sell it");
                } catch (StoreItemNotInSystemException e) {
                    System.out.println("Error! - Some Store is trying to sell an item that's not in system ("+e.ItemIdInput+")");
                } catch (WrongPayingMethodException e) {
                    System.out.println("Error! - Wrong input Paying method - "+e.PayingInput);
                } catch (DuplicateItemIDException e) {
                    System.out.println("Error! - XML contains 2 Items with the same id : "+e.id);
                } catch (NegativePriceException e) {
                    System.out.println("Error! - XML contains a Negative Price "+e.PriceReceived+" for Item!");
                }

            }
            else
                System.out.println("Error - not Type XML (needs to end with <xml name>.xml");
        }
    }


    private void StaticOrder() {

        try {
            List<StoreInfo> listOfAllStoresInSystem = MainSDMSystem.getListOfAllStoresInSystem();
            Date inputDate;
            Point curLocation;
            if (listOfAllStoresInSystem.isEmpty()) {
                System.out.println("Cant Use This Option - No Store in System Yet");
                return;
            }

            for (StoreInfo curStore : listOfAllStoresInSystem)
                System.out.println("store #"+curStore.StoreID+ " - "+curStore.Name+" PPK is "+curStore.PPK);

            System.out.println("Please Type Store ID from the list above:");

            StoreInfo StoreChosen = checkValidStore();//4.1
            inputDate = getValidDate(); //4.2
            curLocation = getValidPoint(); //4.3
            Collection<ItemInOrderInfo> ItemsChosen = getValidItemsForOrder(StoreChosen); //4.4

            double ppk = MainSDMSystem.CalculatePPK(StoreChosen.StoreID,curLocation);
            if (approveOrder(ItemsChosen,ppk))//4.5
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
            System.out.println("Current loction is not on grid [0-50] "+e.PointReceived);
        }
    }

    private Point getValidPoint() {
        Point res = new Point(-1,-1);
        boolean flag = true;
        int x=-1,y=-1;

        while (flag) {
            System.out.println("Please Enter Current Location On Grid (between 0-50 for X and Y)");
            System.out.print("For X: ");
            String xStr = scanner.nextLine();
            System.out.print("For Y: ");
            String yStr = scanner.nextLine();
            try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);} catch (NumberFormatException e) {
                System.out.println("Wrong Input - try again");
                scanner.nextLine();
            }
            res = new Point(x,y);
            if (!SuperDuperMarketSystem.isCoordinateInRange(res))
                System.out.println("Please Enter Points between 0-50");
            else
                flag=false;
        }
        return res;
    }

    private Date getValidDate() {
        scanner.nextLine();
        Date res=null;
        boolean flag=true;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("dd/MM-hh:mm");
        while (flag) {

            System.out.println("Please Type Order Date in the form of dd/mm-hh:mm (E.g 02/11-13:56):");
            String inputDateString = scanner.nextLine();

            try {
                res = dateFormat.parse(inputDateString);
                flag=false; //todo valid input - its allow 15/15
            } catch (ParseException e) {
                System.out.println("Date Entered is not in the Form dd/MM-hh:mm");
                System.out.println("Remember Time limits , dd (00-31) MM(01-12) hh(00-23) mm(00-59)"); //todo this date
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Remember Time limits , dd (00-31) MM(01-12) hh(00-23) mm(00-59)"); //todo this date
                scanner.nextLine();} //4.2
        }

        return res;
    }

    private StoreInfo checkValidStore() {
        boolean flag = true;

        while (flag) {
            long input = getValidIDNumber();
            try {
                StoreInfo storeInfo = MainSDMSystem.GetStoreInfoByID(input);
                return storeInfo;
            } catch (InvalidKeyException e) {
                System.out.println("Error - Please choose Store ID by the list above!");
                scanner.nextLine();
            }
        }
        return null;
    }

    private boolean approveOrder(Collection<ItemInOrderInfo> itemsChosen,double PPK) {
        if (itemsChosen.isEmpty())
            return false;

        printLineOfStars();
        for (ItemInOrderInfo curItem : itemsChosen)
            System.out.println("Item #"+curItem.serialNumber+" ("+curItem.Name+") Pay by "+curItem.PayBy+"," +
                    " Price per Unit is "+curItem.PricePerUint+" Amount is "+curItem.amountBought+"," +
                    " Total Cost "+(curItem.amountBought*curItem.PricePerUint)+".");


        System.out.println(String.format("Shipping will Cost you %.2f",PPK));
        printLineOfStars();

        System.out.println("Type Y to Complete Order, or Anything Else To Cancel ");
        String ans =  scanner.next();
        if (ans.equals("Y"))
            return true;
        return false;


    }

    private Collection<ItemInOrderInfo> getValidItemsForOrder(StoreInfo storeChosen) {

        List<ItemInfo> allItem = null;
        long ItemID;
        double amountWanted = 0;
        Map<Long,ItemInOrderInfo> Basket = new HashMap<>();
        boolean flag = true;
        try {
            allItem = MainSDMSystem.getListOfAllItems();
        } catch (NoValidXMLException e) {
            System.out.println("Please Upload a Valid XML before Trying this Options!"); //can ignore here..
        }

        printLineOfEqual();
        for (ItemInfo curItem : allItem) {
            System.out.print(String.format("%d - %s sold by %s", curItem.serialNumber, curItem.Name, curItem.PayBy));
            if (MainSDMSystem.isItemSoldInStore(storeChosen.StoreID, curItem.serialNumber))
                System.out.println(" Price in Store - " + MainSDMSystem.getItemPriceInStore(storeChosen.StoreID, curItem.serialNumber));
            else
                System.out.println(" Item is Not Being Sold In Store");
        }
        printLineOfEqual();


        while (flag) {
            System.out.println("Please Enter Item ID you Want to add to Basket, Enter q to Quit");
            String str = scanner.next();
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
                                amountWanted = scanner.nextDouble();
                                if (amountWanted <= 0)
                                    System.out.println("Please Enter a Positive Number");
                                else
                                    end = true;
                            }catch (InputMismatchException e) {
                                System.out.println("Not a Number! - try again");
                                scanner.nextLine();
                            }
                        }
                    }
                    scanner.nextLine();

                    if (Basket.containsKey(wantedItem.serialNumber)) {
                        System.out.println("Already in Basket - Adding "+ amountWanted);
                        Basket.get(wantedItem.serialNumber).amountBought += amountWanted;
                    }
                    else
                    {
                        Basket.put(wantedItem.serialNumber,new ItemInOrderInfo(wantedItem.serialNumber,
                                wantedItem.Name,wantedItem.PayBy,storeChosen.StoreID,
                                amountWanted,MainSDMSystem.getItemPriceInStore(storeChosen.StoreID, wantedItem.serialNumber)));
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


    private long getValidIDNumber ()
    {
        boolean flag= true;
        long res = 0;
        while (flag)
        {
            try {
                res = scanner.nextLong();
                if (res <= 0)
                    System.out.println("Please Enter a Positive Number");
                else
                flag =false;
        }catch (Exception e) {
            System.out.println("Please enter a number!");
            scanner.nextLine();
        }}
        return res;
    }
    private void DynamicOrder() { //bonus
    }

    private void AddItemToStore() { //bonus
    }

    private void DeleteItemFromStore() { //bonus
    }

    private void ChangeItemPrice() { //bonus
    }

    private void showAllOrders() {
        try {

            int i = 1;
            StringBuilder str = new StringBuilder();
            List<OrderInfo> OrderList= MainSDMSystem.getListOfAllOrderInSystem();
            printLineOfStars();
            if (OrderList.isEmpty())
                System.out.println("No Orders In System Yet!");
            else
                for (OrderInfo CurOrder : OrderList) {
                    str.append(i++ + ". ");
                    str.append("Order #" + CurOrder.m_OrderSerialNumber + " at ");
                    str.append(CurOrder.m_Date);
                    if (CurOrder.Stores.size() == 1)
                        str.append(" From "+ CurOrder.Stores.get(0));
                    else {
                        int j = 1;
                        str.append("Stores in Order are :");
                        for (String curStore : CurOrder.Stores)
                            str.append(j++ + curStore + ".\n");
                    }

                    str.append("\nThe Number of Items in Order: " + CurOrder.m_amountOfItems +
                            "\n Cost of only Items: " + CurOrder.m_ItemsPrice +
                            "\n Cost of Shipping: " + CurOrder.m_ShippingPrice +
                            "\n Cost of Total Order: " + CurOrder.m_TotalPrice);
                    printLineOfStars();
                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        }
        catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        }
    } //check

    private void showAllItems() {

        try {
            int i = 1;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
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
                    str.append("Average Price is : " + df.format(CurItem.AvgPrice)); //todo all avg needs to be 2 digit
                    str.append("\nWas sold  : " + CurItem.SoldCount + " times.");
                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        } catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Enter a Valid XML before Trying this Options!");
        }
    } //check

    private void showAllStore () {
        try {
            int i = 1;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            StringBuilder str = new StringBuilder();
            List<StoreInfo> StoresList = MainSDMSystem.getListOfAllStoresInSystem();

            printLineOfStars(); //todo if null
            if (StoresList.isEmpty())
                System.out.println("No Stores In System Yet!");
            else
                for (StoreInfo CurStore : StoresList) {
                    printLineOfEqual();
                    str.append(i++ + ". ");

                    str.append("Store #" + CurStore.StoreID + " \""+ CurStore.Name + "\" \n" +
                            "PPK is: " + CurStore.PPK +
                            " and So Far her Profit from Shipping is :" + CurStore.profitFromShipping + "\n");

                    if (CurStore.Items.isEmpty())
                        str.append("Does Not Sell Items Yet!\n");
                    else {
                        str.append("Selling Items:\n");
                        for (ItemInStoreInfo curItem : CurStore.Items)
                            str.append(curItem.Name + " #" + curItem.serialNumber + " Paying method is by " + curItem.PayBy.toLowerCase() +
                                    " and it's cost " + curItem.PriceInStore + " , Sold " + ((int) curItem.SoldCounter) + " times. \n");
                    }

                    if (CurStore.OrderHistory.isEmpty())
                        str.append("Did Not Sell To Anyone Yet!\n");
                    else {
                        str.append("Order History is:\n");
                        for (OrdersInStoreInfo curOrder : CurStore.OrderHistory)
                            str.append("Order#" + curOrder.OrderSerialNumber +
                                    "Number of Items: " + curOrder.amountOfItems +
                                    "Cost of only Items: " + curOrder.ItemsPrice +
                                    "Cost of Shipping: " + curOrder.ShippingPrice +
                                    "Cost of Total Order: " + curOrder.TotalPrice + "\n");
                    }

                    System.out.println(str);
                    str = new StringBuilder();
                }

            printLineOfStars();
        } catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        }
    } //check

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


}
