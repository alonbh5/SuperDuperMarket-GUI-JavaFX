package course.java.sdm.gui.CreateOrderMenu;

import course.java.sdm.classesForUI.ItemInStoreInfo;

import java.awt.*;

public class ImproveItem  {

    public final ItemInStoreInfo item;
    public final Button AmountByUser = new Button("Amount");

    public ImproveItem(ItemInStoreInfo item) {
        this.item = item;
    }

    public ItemInStoreInfo getItem() {
        return item;
    }

    public Button getAmountByUser() {
        return AmountByUser;
    }

    public Long getSerialNumber() {
        return item.serialNumber;
    }

    public String getName() {
        return item.Name;
    }

    public String getPayBy() {
        return item.PayBy;
    }

    public Double getPriceInStore() {
        return item.PriceInStore;
    }

    public Double getSoldCounter() {
        return item.SoldCounter;
    }
}
