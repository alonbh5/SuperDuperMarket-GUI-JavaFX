package course.java.sdm.console;
import course.java.sdm.engine.SuperDuperMarketSystem;
import course.java.sdm.exceptions.NoValidXMLException;

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
        ConsoleMenuBuilder UploadXMLMenu = new ConsoleMenuBuilder("Upload System XML");
        ConsoleMenuBuilder NewOrderMenu = new ConsoleMenuBuilder("Create New Order");
        ConsoleMenuBuilder SaveOrderMenu = new ConsoleMenuBuilder("Save Orders To XML");
        ConsoleMenuBuilder LoadOrderMenu = new ConsoleMenuBuilder("Load Orders XML");
        ConsoleMenuBuilder ChangeItemsMenu = new ConsoleMenuBuilder("Change,Add or Delete Item In Store");

        ChangeItemsMenu.AddMenuItem("Delete Item From Store",this::DeleteItemFromStore);
        ChangeItemsMenu.AddMenuItem("Add new Item To Store",this::AddItemToStore);
        ChangeItemsMenu.AddMenuItem("Change Item's Price From Store",this::ChangeItemPrice);

        ChangeItemsMenu.AddMenuItem("Static Order",this::StaticOrder);
        ChangeItemsMenu.AddMenuItem("Dynamic Order",this::DynamicOrder);
        
        
        MainMenu.AddMenuItem(UploadXMLMenu);
        MainMenu.AddMenuItem("View All Stores",this::showAllStore);
        MainMenu.AddMenuItem("View All Items",this::showAllItems);
        MainMenu.AddMenuItem(NewOrderMenu);
        MainMenu.AddMenuItem("Show Orders History",this::showAllOrders);
        MainMenu.AddMenuItem(ChangeItemsMenu);
        MainMenu.AddMenuItem(SaveOrderMenu);
        MainMenu.AddMenuItem(LoadOrderMenu);
    }

    private static void printLineOfStars ()
    {
        System.out.println("********************************************************");
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
            List<String> OrderList= MainSDMSystem.getListOfAllOrderInSystem();
            printLineOfStars();
            if (OrderList.isEmpty())
                System.out.println("No Orders In System Yet!");
            else
                for (String CurStore : OrderList)
                    System.out.println(i++ +". "+ CurStore);

            printLineOfStars();
        }
        catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
        }
    }

    private void showAllItems() {

        try {
            int i = 1;
            List<String> itemsList= MainSDMSystem.getListOfAllItems();
            printLineOfStars();
            if (itemsList.isEmpty())
                System.out.println("No Items In System Yet!");
            else
                for (String CurItem : itemsList)
                    System.out.println(i++ +". "+ CurItem);

            printLineOfStars();
        } catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Enter a Valid XML before Trying this Options!");
        }
    }

    private void showAllStore ()
    {
        try {
            int i = 1;
            List<String> StoresList= MainSDMSystem.getListOfAllStoresInSystem();
            printLineOfStars();
            if (StoresList.isEmpty())
                System.out.println("No Stores In System Yet!");
            else
                for (String CurStore : StoresList)
                    System.out.println(i++ +". "+ CurStore);

            printLineOfStars();
        }
        catch (NoValidXMLException e) //todo more exception??
        {
            System.out.println("Please Upload a Valid XML before Trying this Options!");
    }}

}
