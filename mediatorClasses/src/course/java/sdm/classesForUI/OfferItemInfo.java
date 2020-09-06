package course.java.sdm.classesForUI;

public class OfferItemInfo {
    public final Long ID;
    public final String Name;
    public final String PayBy;
    public final Double Amount;
    public final Double PricePerOne;

    public OfferItemInfo(Long ID, String name, String payBy, Double amount, Double pricePerOne) {
        this.ID = ID;
        Name = name;
        PayBy = payBy;
        Amount = amount;
        PricePerOne = pricePerOne;
    }
}
