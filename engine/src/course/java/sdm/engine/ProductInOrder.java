package course.java.sdm.engine;

public class ProductInOrder {

        private final ProductInStore productInStore;
        private double amountBought;
        private double PriceOfTotalItems;

    public ProductInOrder(ProductInStore productInStore) {
        this.productInStore = productInStore;
    }

    public void setAmountBought(double amountBought) {
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
}

