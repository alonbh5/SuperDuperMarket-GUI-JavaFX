package course.java.sdm.classesForUI;
import java.awt.*;

public class CustomerInfo {

    public final String name;
    public final Long ID;
    public final Point Location;
    public final Double AvgPriceForShipping;
    public final Double AvgPriceForOrderWithoutShipping;
    public final Integer AmountOfOrders; //todo list of orderinfo?

    public CustomerInfo(String name, long ID, Point location, double avgPriceForShipping, double avgPriceForOrderWithoutShipping, int amountOfOrders) {
        this.name = name;
        this.ID = ID;
        Location = location;
        AvgPriceForShipping = avgPriceForShipping;
        AvgPriceForOrderWithoutShipping = avgPriceForOrderWithoutShipping;
        AmountOfOrders = amountOfOrders;
    }
}
