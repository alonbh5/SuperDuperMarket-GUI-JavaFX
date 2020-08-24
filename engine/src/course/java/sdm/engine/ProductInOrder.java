package course.java.sdm.engine;

import java.io.Serializable;
import java.util.Objects;

public class ProductInOrder implements Serializable {

        private final ProductInStore productInStore;
        private double amountBought;
        private double PriceOfTotalItems;

     ProductInOrder(ProductInStore productInStore) {
        this.productInStore = productInStore;
    }

     void setAmountBought(double amountBought) {
        this.amountBought = amountBought;
        this.PriceOfTotalItems = amountBought * productInStore.getPricePerUnit();
    }

    public void addAmountBought(double amountBought) {
        this.amountBought += amountBought;
        this.PriceOfTotalItems = amountBought * productInStore.getPricePerUnit();
    }



    public ProductInStore getProductInStore() { return productInStore;  }

    public double getAmount() {
        return amountBought;
    }

    public int getAmountByPayingMethod ()
    {
        int Amount = 1;
        if (getPayBy().equals(Item.payByMethod.AMOUNT))
            Amount = ((int) getAmount());
        return Amount;
    }

    public double getPriceOfTotalItems() {
        return PriceOfTotalItems;
    }

    public Long getSerialNumber () {return productInStore.getItem().serialNumber;}

    protected Item.payByMethod getPayBy() {
        return productInStore.getItem().getPayBy();
    }

    // todo hash equals toString


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrder that = (ProductInOrder) o;
        return Double.compare(that.amountBought, amountBought) == 0 &&
                Double.compare(that.PriceOfTotalItems, PriceOfTotalItems) == 0 &&
                productInStore.equals(that.productInStore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInStore, amountBought, PriceOfTotalItems);
    }
}

