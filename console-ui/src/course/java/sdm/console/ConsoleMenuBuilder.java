package course.java.sdm.console;


import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class ConsoleMenuBuilder {

    protected final List<MenuItem> r_MenuItems =new ArrayList<MenuItem>();
    private String m_Title;
    private int m_Index = 0;
    private int m_Level;

    Scanner scanner = new Scanner(System.in);

    public ConsoleMenuBuilder(String i_Title) {
        //// Default State of any menu is MainMenu (level is 1, option 0 is exit system)
        m_Title = i_Title;
        m_Level = 1;
        AddMenuItem("Exit", new Runnable() {
            @Override
            public void run() {
                System.exit(-1);
            }
        });
    }

    private String getTitle () {return m_Title;}

    private int getLevel() {return m_Level;}

    private int getIndex () {return m_Index; }


    public void AddMenuItem(String i_MenuItemTitle, Runnable i_FunctionToAdd) {
        //// Adds new item to the menu - gets item name, and function to do
        //// Add the item to next available index in menu

        r_MenuItems.add(new MenuItem(m_Index++, i_MenuItemTitle, i_FunctionToAdd));
    }

    public void AddMenuItem(ConsoleMenuBuilder io_SubMenu) {
        //// Gets a new SubMenu to add the menu.
        //// Change SubMenu option 0 from "Exit" to "Back" & add the SubMenu to next available index in menu.
        //// Update both [option 0 and new sub menu] flag "IsMenu" to be recognized as submenu.

        io_SubMenu.r_MenuItems.get(0).setTitle("Back");
        io_SubMenu.r_MenuItems.get(0).setMenu(true);
        io_SubMenu.r_MenuItems.get(0).setClick(new Runnable() {
            @Override
            public void run() {
                Show();
            }});
        io_SubMenu.r_MenuItems.get(0).setClick(this::Show);
        ;

        AddMenuItem(io_SubMenu.m_Title,io_SubMenu::Show);
        r_MenuItems.get(r_MenuItems.size() - 1).setMenu(true);
    }

    private static void exitSystem() {
        System.exit(-1);
    }

    public void Show() {
        boolean quit = false;
        int choice=-1;
        printMenu();

        while (!quit) {
            choice=getInput();

            r_MenuItems.get(choice).OnClicked();

            if (r_MenuItems.get(choice).IsMenu()) {
                quit = true;
            } else {
                System.out.println("Press Enter to continue..."); // todo fix enter will continue
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                printMenu();
            }
        }
    }

    private void printMenu() {

        System.out.println("=================="+this.m_Title+"==================");

        for(MenuItem currMenuItem : r_MenuItems)
        {
            currMenuItem.Show();
        }
    }

    private int getInput() {
        int userIntegerInput = -1;
        boolean validInput = false;


        System.out.println("Please Choose One Of Options Above By Number:");

        do {
            try {
                userIntegerInput = scanner.nextInt();
                if (userIntegerInput < 0 || userIntegerInput >= r_MenuItems.size()) {
                    System.out.println("There is no option " + userIntegerInput + " - Try Again!");
                    scanner.nextLine();
                }
                else
                    validInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("This is not a number - Try Again!");
                validInput = false;
                scanner.nextLine();
            }
        } while (validInput == false);

        return userIntegerInput;
    }
}
