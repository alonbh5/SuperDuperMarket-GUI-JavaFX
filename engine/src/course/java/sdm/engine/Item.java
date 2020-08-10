package course.java.sdm.engine;

import java.util.Objects;

public  class Item implements HasName {

    private static int serialGenerator = 10000;
    public enum payByMethod {
        AMOUNT, WEIGHT
    }

    private final int serialNumber;
    private String Name;
    private final payByMethod PayBy;

    public Item(String i_Name, payByMethod howItsPaid) {
        this.serialNumber = serialGenerator++;
        Name = i_Name;
        PayBy = howItsPaid;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String Input) {
        Name = Input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return serialNumber == item.serialNumber &&
                Objects.equals(Name, item.Name) &&
                PayBy == item.PayBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, Name, PayBy);
    }
}
