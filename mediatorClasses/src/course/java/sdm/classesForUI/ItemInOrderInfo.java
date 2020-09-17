package course.java.sdm.classesForUI;

public class ItemInOrderInfo {

    public final Long serialNumber;
    public final Boolean FromSale;
    public final String Name;
    public final String PayBy;
    public final Long FromStoreID;
    public Double amountBought;
    public final Double PricePerUint;
    public final Double TotalPrice;

    public ItemInOrderInfo(long serialNumber, String name, String payBy, long fromStoreID, double amountBought, double PricePerUnit,double totalPrice,Boolean fromSale) {
        this.serialNumber = serialNumber;
        Name = name;
        PayBy = payBy;
        FromStoreID = fromStoreID;
        this.amountBought = amountBought;
        PricePerUint = PricePerUnit;
        this.TotalPrice = totalPrice;
        FromSale = fromSale;
    }

    public ItemInOrderInfo(long serialNumber, double amountBought) {
        this.serialNumber = serialNumber;
        this.amountBought = amountBought;
        this.PayBy = null;
        this.Name = null;
        this.FromStoreID= Long.valueOf(0);
        this.PricePerUint = 0.0;
        this.TotalPrice=0.0;
        FromSale=false;
    }

    public ItemInOrderInfo(ItemInStoreInfo item, Double Amount) {
        this.serialNumber = item.serialNumber;
        this.amountBought = Amount;
        this.PayBy = item.PayBy;
        this.Name = item.Name;
        this.FromStoreID= 0L;
        this.PricePerUint = item.PriceInStore;
        this.TotalPrice=Amount*PricePerUint;
        FromSale=false;
    }

    public void addAmount(double AmountToAdd) {
        this.amountBought+=AmountToAdd;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public Boolean getFromSale() {
        return FromSale;
    }

    public String getName() {
        return Name;
    }

    public String getPayBy() {
        return PayBy;
    }

    public Long getFromStoreID() {
        return FromStoreID;
    }

    public Double getAmountBought() {
        return Double.parseDouble(String.format("%.2f", amountBought));
    }

    public Double getPricePerUint() {
        return Double.parseDouble(String.format("%.2f", PricePerUint));
    }

    public Double getTotalPrice() {
        return Double.parseDouble(String.format("%.2f", TotalPrice));
    }

    public String getIsDiscount() {
        if (FromSale)
            return "Discount";
        else
            return "Regular";
    }
}
