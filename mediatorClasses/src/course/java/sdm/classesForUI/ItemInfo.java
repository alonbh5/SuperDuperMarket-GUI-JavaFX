package course.java.sdm.classesForUI;

public class ItemInfo {

    public final long serialNumber;
    public final String Name;
    public final String PayBy;
    public final double AvgPrice;
    public final int NumOfSellingStores;
    public final int SoldCount;

    public ItemInfo(long serialNumber, String name, String payBy, double avgPrice, int numOfSellingStores, int soldCount) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        AvgPrice = avgPrice;
        NumOfSellingStores = numOfSellingStores;
        SoldCount = soldCount;
    }
}
