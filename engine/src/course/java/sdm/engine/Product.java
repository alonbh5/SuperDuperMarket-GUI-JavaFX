package course.java.sdm.engine;

import course.java.sdm.exceptions.NegativePrice;

import java.security.InvalidParameterException;

public class Product {

    private final Long ItemID;
    private final Long StoreID;
    private final double PricePerUnit;
    private final Item.payByMethod PayBy;
    private double Amount=0.0;
    private double TotalPrice;

    public Product(Long itemID, Long storeID, double pricePerUnit ,Item.payByMethod payBy) {
        ItemID = itemID; //what item it is
        StoreID = storeID; //where its sold
        PricePerUnit = pricePerUnit;
        PayBy = payBy;

        if (pricePerUnit <= 0)
            throw (new NegativePrice(pricePerUnit));

    }

    public Product(Long itemID, Long storeID, double pricePerUnit, double amount,Item.payByMethod payBy) {
        ItemID = itemID;
        StoreID = storeID;
        PricePerUnit = pricePerUnit;
        PayBy = payBy;
        setAmount(amount);

        if (pricePerUnit <= 0)
            throw (new NegativePrice(pricePerUnit));
    }

    void setAmount (double i_amount)
    {
        if (i_amount <= 0)
            throw (new InvalidParameterException ("Amount" + i_amount + " Is Non Positive"));

        if (PayBy.equals(Item.payByMethod.AMOUNT) && i_amount % 1 != 0) //todo check this
                throw (new InvalidParameterException ("Amount" + i_amount + " Is not an Integer for Paying Method: by Amount"));

        this.TotalPrice = this.PricePerUnit * i_amount;
    }

    public Long getItemID() {
        return ItemID;
    }

    public Long getStoreID() {
        return StoreID;
    }

    public double getPricePerUnit() {
        return PricePerUnit;
    }

    public Item.payByMethod getPayBy() {
        return PayBy;
    }

    public double getAmount() {
        return Amount;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }
}
