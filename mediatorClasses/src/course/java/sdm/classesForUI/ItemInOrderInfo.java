package course.java.sdm.classesForUI;

public class ItemInOrderInfo {

    public final long serialNumber;
    public final String Name;
    public final String PayBy;
    public final long FromStoreID;
    public double amountBought;
    public final double PricePerUint;
    public final double TotalPrice;

    public ItemInOrderInfo(long serialNumber, String name, String payBy, long fromStoreID, double amountBought, double PricePerUnit,double totalPrice) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        FromStoreID = fromStoreID;
        this.amountBought = amountBought;
        PricePerUint = PricePerUnit;
        this.TotalPrice = totalPrice;
    }

    public ItemInOrderInfo(long serialNumber, double amountBought) {
        this.serialNumber = serialNumber;
        this.amountBought = amountBought;
        this.PayBy = null;
        this.Name = null;
        this.FromStoreID=0;
        this.PricePerUint = 0;
        this.TotalPrice=0;
    }
}
