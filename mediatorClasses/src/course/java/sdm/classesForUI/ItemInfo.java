package course.java.sdm.classesForUI;

public class ItemInfo {

    public final Long serialNumber;
    public final String Name;
    public final String PayBy;
    public final Double AvgPrice;
    public final Integer NumOfSellingStores;
    public final Integer SoldCount;

    public ItemInfo(long serialNumber, String name, String payBy, double avgPrice, int numOfSellingStores, int soldCount) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        AvgPrice = avgPrice;
        NumOfSellingStores = numOfSellingStores;
        SoldCount = soldCount;
    }
}
