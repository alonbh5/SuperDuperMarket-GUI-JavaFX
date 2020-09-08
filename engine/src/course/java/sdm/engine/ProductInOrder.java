package course.java.sdm.engine;

import java.io.Serializable;
import java.util.Objects;

class ProductInOrder implements Serializable {

        private final ProductInStore productInStore;
        private final boolean isFromSale;
        private double amountBought;
        private double PriceOfTotalItems;

     ProductInOrder(ProductInStore productInStore,boolean isFromSale) {

         this.productInStore = productInStore;
         this.isFromSale =isFromSale;
    }

     void setAmountBought(double amountBought) {
        this.amountBought = amountBought;
        this.PriceOfTotalItems = amountBought * productInStore.getPricePerUnit();
    }

    void addAmountBought(double amountBought) {
        this.amountBought += amountBought;
        this.PriceOfTotalItems = amountBought * productInStore.getPricePerUnit();
    }



    ProductInStore getProductInStore() { return productInStore;  }

    double getAmount() {
        return amountBought;
    }

    int getAmountByPayingMethod ()
    {
        int Amount = 1;
        if (getPayBy().equals(Item.payByMethod.AMOUNT))
            Amount = ((int) getAmount());
        return Amount;
    }

    double getPriceOfTotalItems() {
        return PriceOfTotalItems;
    }

    Long getSerialNumber () {return productInStore.getItem().getSerialNumber();}

    Item.payByMethod getPayBy() {
        return productInStore.getItem().getPayBy();
    }




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

