package course.java.sdm.engine;

import java.util.Objects;

public  class Item implements HasName {

    public enum payByMethod {
        AMOUNT, WEIGHT
    }

    protected final long serialNumber;
    protected String Name;
    protected final payByMethod PayBy;

    Item(Long i_serialNumber,String i_Name, payByMethod e_howItsPaid) {
        this.serialNumber = i_serialNumber;
        Name = i_Name;
        PayBy = e_howItsPaid;
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
        return Objects.hash(serialNumber);
    }

    protected long getSerialNumber() {
        return serialNumber;
    }

    protected payByMethod getPayBy() {
        return PayBy;
    }

    @Override
    public String toString() {
        return  Name +
                ", Serial #" + serialNumber +
                ", Paying Method is: By " + PayBy.toString().toLowerCase()+".";

    }
}
