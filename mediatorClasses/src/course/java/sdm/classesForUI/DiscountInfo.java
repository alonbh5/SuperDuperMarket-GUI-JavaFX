package course.java.sdm.classesForUI;

import course.java.sdm.engine.Discount;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class DiscountInfo {

    public final String Name;
    public final String DiscountOperator;
    public final OfferItemInfo itemToBuy;
    public final Double AmountToBuy;
    public final Long StoreID;
    public final List<OfferItemInfo> OfferedItem;
    public final List<Integer> IndexOfWantedItem = new ArrayList<>();
    private int MaxAmount;
    private int i = 0;

    public SimpleIntegerProperty AmountEntitled = new SimpleIntegerProperty(0);
    public SimpleIntegerProperty AmountWanted = new SimpleIntegerProperty(0);

    public DiscountInfo(String name, String discountOperator, OfferItemInfo itemToBuy, Double amountToBuy, List<OfferItemInfo> offeredItem,Long StoreID) {
        Name = name;
        DiscountOperator = discountOperator;
        this.itemToBuy = itemToBuy;
        AmountToBuy = amountToBuy;
        if (offeredItem == null)
            OfferedItem = new ArrayList<>();
        else
            OfferedItem = offeredItem;
        this.StoreID = StoreID;
    }

    public void setAmountEntitled(Integer amountEntitled) //engine do this
    {
        AmountEntitled.setValue(amountEntitled);
        MaxAmount= amountEntitled;
    }

    public Integer getAmountEntitled() {
        return AmountEntitled.getValue();
    }

    public void addAmountWanted() { // UI do this
        if (AmountWanted.getValue()+1 <= MaxAmount) {
            AmountWanted.setValue(AmountWanted.getValue()+1);
            AmountEntitled.setValue(AmountEntitled.getValue()-1);
        }

    }

    public void addListener (DiscountInfo affectedDiscount) { //i want other discount to go down if other buy
        AmountWanted.addListener( (observable, oldValue, newValue) -> {
                affectedDiscount.OnOtherSelected();});

    }

    public void OnOtherSelected () {
        AmountEntitled.setValue(AmountEntitled.getValue()-1);
    }

    public void setIndexOfWantedItem(Integer indexOfWantedItem) {
        if (DiscountOperator.toLowerCase().equals("one_of")) //todo check this
            IndexOfWantedItem.add(indexOfWantedItem);
    }

    public boolean isIndex() {
        return DiscountOperator.toLowerCase().equals("one_of");
    }

    public int getIndex(int i) {
        return IndexOfWantedItem.get(i++);
    }

    public void addItemYouGet (OfferItemInfo newItem) {
        boolean flag =true;
        for (OfferItemInfo cur : OfferedItem) {
            if (cur.ID.equals(newItem.ID))
                if (cur.Amount.equals(newItem.Amount))
                    if (cur.PricePerOne.equals(newItem.PricePerOne)) {
                        flag = false;
                        break; //todo check that works
                    }
        }

         if (flag)
             OfferedItem.add(newItem);
    }


}
