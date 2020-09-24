package course.java.sdm.engine;

import java.io.Serializable;
import java.util.Objects;

class ProductInStore implements Serializable {

    private final Item item;
    private double pricePerUnit;
    private final Store store;
    private double amountSoldInStore=0;

    ProductInStore(Item item, double pricePerUnit,Store store) {
        this.item = item;
        this.store =store;
        this.pricePerUnit = pricePerUnit;
    }

    long getSerialNumber () {return item.getSerialNumber();}

    double getPricePerUnit() {
        return pricePerUnit;
    }



    Item getItem() {
        return item;
    }

    Store getStore() {
        return store;
    }

    double getAmountSold() {
        return amountSoldInStore;
    }

    void addAmount (int amountToAdd)
    {
        amountSoldInStore+=amountToAdd;
    }

    @Override
    public String toString() {
        return this.item +
                "Cost: "+ this.getPricePerUnit() +
                "Sold "+this.amountSoldInStore + "so far.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInStore that = (ProductInStore) o;
        return that.getItem().getSerialNumber() == this.item.getSerialNumber() && that.getStore().equals(this.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, pricePerUnit, store, amountSoldInStore);
    }

    void setPrice(double newPrice) {
        this.pricePerUnit = newPrice;
    }
}

