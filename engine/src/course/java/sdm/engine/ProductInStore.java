package course.java.sdm.engine;

import course.java.sdm.exceptions.NegativePrice;

import java.security.InvalidParameterException;

public class ProductInStore {

    private Item item;
    private double PricePerUnit;
    private int AmountSold=0;

    public ProductInStore(Item item, double pricePerUnit) {
        this.item = item;
        PricePerUnit = pricePerUnit;
    }

    public double getPricePerUnit() {
        return PricePerUnit;
    }


}
