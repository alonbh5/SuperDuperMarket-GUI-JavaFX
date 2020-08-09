package course.java.sdm.console;

import course.java.sdm.engine.Engine;

public class Main {

    public static void main(String[] args) {

        ConsoleMenuBuilder mymenu = new ConsoleMenuBuilder("Main menu");
        ConsoleMenuBuilder mymenu2 = new ConsoleMenuBuilder("seconde menu");

        mymenu.AddMenuItem("goo to", Main::goo);

        mymenu.AddMenuItem("foo to", Main::foo);

        mymenu.AddMenuItem("koo to", Main::koo);
        mymenu.AddMenuItem(mymenu2);


        mymenu.Show();


    }


    public  static void foo ()
    {
        System.out.println("in foo");
    }

    public  static void koo ()
    {
        System.out.println("in koo");
    }

    public  static void goo ()
    {
        System.out.println("in goo");
    }
}
