package course.java.sdm.classesForUI;
import java.awt.*;

public class CustomerInfo {

    public final String name;
    public final long ID;
    public final Point Location;
    public final double AvgPriceForShipping;
    public final double AvgPriceForOrderWithoutShipping;
    public final int AmountOfOrders; //todo list of orderinfo?

    public CustomerInfo(String name, long ID, Point location, double avgPriceForShipping, double avgPriceForOrderWithoutShipping, int amountOfOrders) {
        this.name = name;
        this.ID = ID;
        Location = location;
        AvgPriceForShipping = avgPriceForShipping;
        AvgPriceForOrderWithoutShipping = avgPriceForOrderWithoutShipping;
        AmountOfOrders = amountOfOrders;
    }
}
