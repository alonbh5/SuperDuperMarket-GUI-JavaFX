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
        MainMenu.Show();
    }

    private void BuildMainMenu ()
    {
        ConsoleMenuBuilder XMLMenu = new ConsoleMenuBuilder("Upload XML");
        //ConsoleMenuBuilder StoresMenu = new ConsoleMenuBuilder("View All Stores");
        ConsoleMenuBuilder ItemsMenu = new ConsoleMenuBuilder("View All Items");
        ConsoleMenuBuilder NewOrderMenu = new ConsoleMenuBuilder("Create Order");
        //ConsoleMenuBuilder OrderHistoryMenu = new ConsoleMenuBuilder("Show Orders History");
        ConsoleMenuBuilder ExitMenu = new ConsoleMenuBuilder("Exit System");



        MainMenu.AddMenuItem(XMLMenu);
        MainMenu.AddMenuItem("View All Stores",this::showAllStore);
        MainMenu.AddMenuItem("View All Items",this::showAllItems);
        MainMenu.AddMenuItem(NewOrderMenu);
        MainMenu.AddMenuItem("Show Orders History",this::showAllOrders);
        MainMenu.AddMenuItem(ExitMenu);


        //MainMenu.AddMenuItem("View All Items", Main::koo);

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
            System.out.println("Please Enter a Valid XML before Trying this Options!");
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
            System.out.println("Please Enter a Valid XML before Trying this Options!");
    }}

    private static void printLineOfStars ()
    {
        System.out.println("********************************************************");
    }
}
