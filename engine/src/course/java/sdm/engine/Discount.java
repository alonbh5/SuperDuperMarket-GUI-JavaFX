package course.java.sdm.engine;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Discount {

    enum OfferType {
        IRRELEVANT, ONE_OF,ALL_OR_NOTHING
    }

    private OfferType OfferType;
    private String DiscountName;
    private ProductYouBuy whatYouBuy;
    private final Set<ProductYouGet> OffersBasket = new HashSet<>();

    Discount(Discount.OfferType offerType, String discountName, ProductYouBuy whatYouBuy) {
        this.OfferType = offerType;
        this.DiscountName = discountName;
        this.whatYouBuy = whatYouBuy;
    }

    void AddProductYouGet (ProductYouGet ProductToAdd) {
        OffersBasket.add(ProductToAdd);
    }

    public Discount.OfferType getOfferType() {
        return OfferType;
    }

    public String getDiscountName() {
        return DiscountName;
    }

    public ProductYouBuy getWhatYouBuy() {
        return whatYouBuy;
    }

    public Collection<ProductYouGet> getOffersBasket() {
        return OffersBasket;
    }


    boolean isItemYouBuyInDiscount (long itemYouBuyID) {
        return itemYouBuyID == whatYouBuy.getItem().getSerialNumber();
    }

    boolean isItemYouGetInDiscount (long itemYouGetID) {
        for (ProductYouGet cur : getOffersBasket())
            if (cur.getItem().getSerialNumber() == itemYouGetID)
                return true;

     return false;
    }
}
