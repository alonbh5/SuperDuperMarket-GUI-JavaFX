package course.java.sdm.classesForUI;

public class ItemInStoreInfo {

    public final long serialNumber;
    public final String Name;
    public final String PayBy;
    public final double PriceInStore;
    public final double SoldCounter;


    public ItemInStoreInfo(long serialNumber, String name, String payBy, double priceInStore, double soldCount) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        PriceInStore = priceInStore;
        SoldCounter = soldCount;
    }

    public ItemInStoreInfo(long serialNumber, double priceInStore) {
        this.serialNumber = serialNumber;
        PriceInStore = priceInStore;
        this.Name=null;
        this.PayBy=null;
        this.SoldCounter=0;
    }
}
