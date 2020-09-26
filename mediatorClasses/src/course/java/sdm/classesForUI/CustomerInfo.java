package course.java.sdm.classesForUI;
import java.awt.*;

public class CustomerInfo {

    public final String name;
    public final Long ID;
    public final Point Location;
    public final Double AvgPriceForShipping;
    public final Double AvgPriceForOrderWithoutShipping;
    public final Integer AmountOfOrders;

    public CustomerInfo(String name, long ID, Point location, double avgPriceForShipping, double avgPriceForOrderWithoutShipping, int amountOfOrders) {
        this.name = name;
        this.ID = ID;
        Location = location;
        AvgPriceForShipping = avgPriceForShipping;
        AvgPriceForOrderWithoutShipping = avgPriceForOrderWithoutShipping;
        AmountOfOrders = amountOfOrders;
    }

    public String getLocationString (){
        return ("("+(int)Location.getX()+","+(int)Location.getY()+")");
    }

    public String getName() {
        return name;
    }

    public Long getID() {
        return ID;
    }

    public String getLocation() {
        return getLocationString();
    }

    public Double getAvgPriceForShipping() {
        return Double.parseDouble(String.format("%.2f", AvgPriceForShipping));
    }

    public Double getAvgPriceForOrderWithoutShipping() {
        return Double.parseDouble(String.format("%.2f", AvgPriceForOrderWithoutShipping));
    }

    public Integer getAmountOfOrders() {
        return AmountOfOrders;
    }

    @Override
    public String toString() {
        return "Customer #"+ID+
                " (" + name +
                ") , Location at " + getLocationString() ;}

}
