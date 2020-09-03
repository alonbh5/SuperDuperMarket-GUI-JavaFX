package course.java.sdm.engine;

public class ProductYouGet {

    private Item m_item;
    private double m_AmountYouGet;
    private double m_PricePerOne;

     ProductYouGet(Item m_item, double m_AmountYouGet, double m_PriceToAdd) {
        this.m_item = m_item;
        this.m_AmountYouGet = m_AmountYouGet;
        this.m_PricePerOne = m_PriceToAdd;
    }

     Item getItem() {
        return m_item;
    }

     double getAmountYouGet() {
        return m_AmountYouGet;
    }

     double getPriceToAdd() {
        return m_PricePerOne;
    }
}
