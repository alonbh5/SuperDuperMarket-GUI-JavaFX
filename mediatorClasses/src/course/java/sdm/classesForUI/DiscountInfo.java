package course.java.sdm.classesForUI;

import java.util.List;

public class DiscountInfo {

    public final String Name;
    public final String DiscountOperator;
    public final OfferItemInfo itemToBuy;
    public final Double AmountToBuy;
    public final List<OfferItemInfo> OfferedItem;
    public Integer AmountEntitled = 0;

    public DiscountInfo(String name, String discountOperator, OfferItemInfo itemToBuy, Double amountToBuy, List<OfferItemInfo> offeredItem) {
        Name = name;
        DiscountOperator = discountOperator;
        this.itemToBuy = itemToBuy;
        AmountToBuy = amountToBuy;
        OfferedItem = offeredItem;
    }

    public void setAmountEntitled(Integer amountEntitled) {
        AmountEntitled = amountEntitled;
    }
}
