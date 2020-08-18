package course.java.sdm.console;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.NoValidXMLException;
import course.java.sdm.classesForUI.*;

import java.text.DecimalFormat;
import java.util.*;

public class SDMConsoleUI {

    SuperDuperMarketSystem MainSDMSystem;
    Scanner scanner = new Scanner(System.in);
    ConsoleMenuBuilder MainMenu = new ConsoleMenuBuilder("Super Duper Market");

    public SDMConsoleUI() { //todo input is XML
        //get xml address...
        BuildMainMenu();
        MainSDMSystem = new SuperDuperMarketSystem(); //change..
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

        ChangeItemsMenu.AddMenuItem("Static Order",this::StaticOrder);
        ChangeItemsMenu.AddMenuItem("Dynamic Order",this::DynamicOrder);
        
        
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
            str = scanner.nextLine();
            if( checkValidXmlNameEnding(str)) {
                str = "/files1/ex1-small.xml";
                MainSDMSystem.UploadInfoFromXML(str);
            }
            else
                System.out.println("Error - not Type XML (needs to end with <xml name>.xml");
        }
    }


    private void StaticOrder() {
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
                    str.append("Order#" + CurOrder.m_OrderSerialNumber + "at " + CurOrder.m_Date.toString());
                    if (CurOrder.Stores.size() == 1)
                        str.append("From "+ CurOrder.Stores.get(0));
                    else {
                        int j = 1;
                        str.append("Stores in Order are :");
                        for (String curStore : CurOrder.Stores)
                            str.append(j++ + curStore + ".\n");
                    }

                    str.append("Number of Items: " + CurOrder.m_amountOfItems +
                            "\n Cost of only Items: " + CurOrder.m_ItemsPrice +
                            "\n Cost of Shipping: " + CurOrder.m_ShippingPrice +
                            "\n Cost of Total Order: " + CurOrder.m_TotalPrice;
                    System.out.println(str);
                    str = null;
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
                    str.append(i++ + ". ");
                    str.append(CurItem.Name +", Serial #" + CurItem.serialNumber +", Paying Method is: By " + CurItem.PayBy.toLowerCase()+".\n");
                    str.append("Being Sold in " + CurItem.NumOfSellingStores + " Stores. \n");
                    str.append("Average Price is : " + df.format(CurItem.AvgPrice)); //todo all avg needs to be 2 digit
                    str.append("\nWas sold  : " + CurItem.SoldCount + " times.");
                    System.out.println(str);
                    str = null;
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
                    str.append(i++ + ". ");

                    str.append("Store #" + CurStore.StoreID + "\" " + CurStore.Name + "\" \n" +
                            "PPK is: " + CurStore.PPK +
                            " So Far Profit from Shipping is :" + CurStore.profitFromShipping + "\n");

                    if (CurStore.Items.isEmpty())
                        str.append("Does Not Sell Items Yet!\n");
                    else {
                        str.append("Sold Items:\n");
                        for (ItemInStoreInfo curItem : CurStore.Items)
                            str.append(curItem.Name + " #" + curItem.serialNumber + " Paying method is " + curItem.PayBy.toLowerCase() +
                                    "cost " + curItem.PriceInStore + " and was Sold " + curItem.SoldCounter + " times. \n");
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
                    str = null;
                }

            printLineOfStars();
        } catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        }
    } //check

    private static void printLineOfStars () {System.out.println("********************************************************");
    }

    private boolean checkValidXmlNameEnding (String str) {
        int len = str.length();
        if (len < 4)
            return false;
        String strEnding = str.substring(len - 4 ).toLowerCase();
        return (strEnding.equals(".xml"));
    }


}
