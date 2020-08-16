package course.java.sdm.console;
import course.java.sdm.engine.SuperDuperMarketSystem;
import java.util.*;

public class SDMConsoleUI {

    SuperDuperMarketSystem MainSDMSystem;
    Scanner scanner = new Scanner(System.in);
    ConsoleMenuBuilder MainMenu = new ConsoleMenuBuilder("Super Duper Market");

    public SDMConsoleUI() { //todo input is XML

    }

    private void BuildMainMenu ()
    {
        ConsoleMenuBuilder mymenu = new ConsoleMenuBuilder("Main menu");
        ConsoleMenuBuilder mymenu2 = new ConsoleMenuBuilder("seconde menu");

        mymenu.AddMenuItem("goo to", Main::goo);

        mymenu.AddMenuItem("foo to", Main::foo);

        mymenu.AddMenuItem("koo to", Main::koo);
        mymenu.AddMenuItem(mymenu2);


        mymenu.Show();
    }

    private static void printLineOfStars ()
    {
        System.out.println("****************************************************************************");
    }
}
