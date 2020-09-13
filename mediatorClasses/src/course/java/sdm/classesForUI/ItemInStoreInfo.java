package course.java.sdm.classesForUI;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemInStoreInfo {

    public final Long serialNumber;
    public final String Name;
    public final String PayBy;
    public final Double PriceInStore;
    public final Double SoldCounter;


    public ItemInStoreInfo(long serialNumber, String name, String payBy, double priceInStore, double soldCount) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        PriceInStore = priceInStore;
        SoldCounter = soldCount;
    }

    public ItemInStoreInfo(Long serialNumber, Double priceInStore) {
        this.serialNumber = serialNumber;
        PriceInStore = priceInStore;
        this.Name=null;
        this.PayBy=null;
        this.SoldCounter=0.0;
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

    public Double getPriceInStore() {
        return PriceInStore;
    }

    public Double getSoldCounter() {
        return SoldCounter;
    }
}
