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

    public Long getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return Name;
    }

    public String getPayBy() {
        return PayBy;
    }

    public Double getAvgPrice() {
        return AvgPrice;
    }

    public Integer getNumOfSellingStores() {
        return NumOfSellingStores;
    }

    public Integer getSoldCount() {
        return SoldCount;
    }
}
