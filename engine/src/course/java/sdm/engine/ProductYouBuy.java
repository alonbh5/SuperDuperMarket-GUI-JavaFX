package course.java.sdm.engine;

public class ProductYouBuy {

    private Item m_Item;
    private double m_AmountToBuy;

     ProductYouBuy(Item m_item, double m_AmountToBuy) {
        this.m_Item = m_item;
        this.m_AmountToBuy = m_AmountToBuy;
    }

     Item getItem() {
        return m_Item;
    }

     double getAmountToBuy() {
        return m_AmountToBuy;
    }

}
