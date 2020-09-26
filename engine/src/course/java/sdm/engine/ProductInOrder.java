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

    void addAmountWithSamePriceAsNow (double amountBought) {
        double SalePrice = PriceOfTotalItems/this.amountBought  ;
        this.amountBought = this.amountBought+amountBought;
        this.PriceOfTotalItems = this.amountBought * SalePrice;
    }

    void setAmountBoughtFromSale(double amountBought,double SalePrice) {
        this.amountBought = amountBought;
        this.PriceOfTotalItems = amountBought * SalePrice;
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

    boolean isFromSale () {return isFromSale;}




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInOrder that = (ProductInOrder) o;

        boolean sameSale;
        try {
            if (isFromSale == that.isFromSale)
                sameSale =  (PriceOfTotalItems / amountBought) == (that.getPriceOfTotalItems() /that.getAmount());
            else
                sameSale=true;
        }catch (Exception e) {
            sameSale = false;
        }


        return productInStore.equals(that.productInStore) && sameSale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productInStore, amountBought, PriceOfTotalItems);
    }
}

