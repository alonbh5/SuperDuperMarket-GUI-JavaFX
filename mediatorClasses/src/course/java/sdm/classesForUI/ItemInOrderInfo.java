package course.java.sdm.classesForUI;

public class ItemInOrderInfo {

    public final long serialNumber;
    public final String Name;
    public final String PayBy;
    public final long FromStoreID;
    public double amountBought;
    public final double PricePerUint;

    public ItemInOrderInfo(long serialNumber, String name, String payBy, long fromStoreID, double amountBought, double priceOfTotalItems) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        FromStoreID = fromStoreID;
        this.amountBought = amountBought;
        PricePerUint = priceOfTotalItems;
    }
}
