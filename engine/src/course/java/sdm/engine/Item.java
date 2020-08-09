package course.java.sdm.engine;

public class Item implements HasName {

    private static int serialGenerator = 10000;
    public enum payByMethod {
        AMOUNT, WEIGHT
    }


    private final int serialNumber;
    private String Name;
    private final payByMethod Payby;

    public Item(String i_Name, payByMethod howItsPaid) {
        this.serialNumber = serialGenerator++;
        Name = i_Name;
        Payby = howItsPaid;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String Input) {
        Name = Input;
    }
}
