package course.java.sdm.engine;

import course.java.sdm.exceptions.NegativePrice;

import java.security.InvalidParameterException;

public class ProductInOrder {

        private ProductInStore productInStore;
        private double amountBought;
        private double PriceOfTotalItems;



    public ProductInStore getProductInStore() {
        return productInStore;
    }

    public double getAmount() {
        return amountBought;
    }

    public double getPriceOfTotalItems() {
        return PriceOfTotalItems;
    }

    protected Item.payByMethod getPayBy() {
        return productInStore.getItem().getPayBy();
    }
}

