package course.java.sdm.engine;

public class ProductInOrder {

        private ProductInStore productInStore;
        private double amountBought;
        private double PriceOfTotalItems;



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

