package course.java.sdm.console;

import course.java.sdm.engine.Engine;

public class Main {

    //TODO hash and clone to string....

    public static void main(String[] args) {

        ConsoleMenuBuilder mymenu = new ConsoleMenuBuilder("Main menu");
        ConsoleMenuBuilder mymenu2 = new ConsoleMenuBuilder("seconde menu");

        mymenu.AddMenuItem("goo to", Main::goo);

        mymenu.AddMenuItem("foo to", Main::foo);

        mymenu.AddMenuItem("koo to", Main::koo);
        mymenu.AddMenuItem(mymenu2);


        mymenu.Show();

        SDMConsoleUI SDMByConsole = new SDMConsoleUI();
    }

}