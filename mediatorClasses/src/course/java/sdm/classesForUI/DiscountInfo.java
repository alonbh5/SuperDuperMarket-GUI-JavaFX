package course.java.sdm.classesForUI;

import java.util.List;

public class DiscountInfo {

    public final String Name;
    public final String DiscountOperator;
    public final OfferItemInfo itemToBuy;
    public final double AmountToBuy;
    public final List<OfferItemInfo> OfferedItem;

    public DiscountInfo(String name, String discountOperator, OfferItemInfo itemToBuy, double amountToBuy, List<OfferItemInfo> offeredItem) {
        Name = name;
        DiscountOperator = discountOperator;
        this.itemToBuy = itemToBuy;
        AmountToBuy = amountToBuy;
        OfferedItem = offeredItem;
    }
}
