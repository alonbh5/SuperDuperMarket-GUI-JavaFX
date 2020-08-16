package course.java.sdm.console;
import course.java.sdm.engine.SuperDuperMarketSystem;
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
        ConsoleMenuBuilder StoresMenu = new ConsoleMenuBuilder("View All Stores");
        ConsoleMenuBuilder ItemsMenu = new ConsoleMenuBuilder("View All Items");
        ConsoleMenuBuilder NewOrderMenu = new ConsoleMenuBuilder("Create Order");
        ConsoleMenuBuilder OrderHistoryMenu = new ConsoleMenuBuilder("Show Orders History");
        ConsoleMenuBuilder ExitMenu = new ConsoleMenuBuilder("Exit System");

        MainMenu.AddMenuItem(XMLMenu);
        MainMenu.AddMenuItem(StoresMenu);
        MainMenu.AddMenuItem(ItemsMenu);
        MainMenu.AddMenuItem(NewOrderMenu);
        MainMenu.AddMenuItem(OrderHistoryMenu);
        MainMenu.AddMenuItem(ExitMenu);


        //MainMenu.AddMenuItem("View All Items", Main::koo);

    }

    private static void printLineOfStars ()
    {
        System.out.println("****************************************************************************");
    }
}
