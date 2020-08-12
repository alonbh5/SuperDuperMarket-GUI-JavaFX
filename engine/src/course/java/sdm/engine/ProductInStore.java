package course.java.sdm.engine;

import course.java.sdm.exceptions.NegativePrice;

import java.security.InvalidParameterException;
import java.util.Objects;

public class ProductInStore {

    private final Item item;
    private double pricePerUnit;
    private final Store store;
    private double amountSoldInStore=0;

    public ProductInStore(Item item, double pricePerUnit,Store store) {
        this.item = item;
        this.store =store;
        this.pricePerUnit = pricePerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public Item getItem() {
        return item;
    }

    public Store getStore() {
        return store;
    }

    public double getAmountSold() {
        return amountSoldInStore;
    }

    public void addAmount (int amountToAdd)
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
        return Double.compare(that.pricePerUnit, pricePerUnit) == 0 &&
                store == that.store &&
                amountSoldInStore == that.amountSoldInStore &&
                Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, pricePerUnit, store, amountSoldInStore);
    }
}

