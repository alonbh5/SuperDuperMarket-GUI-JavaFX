package course.java.sdm.engine;

public class ProductYouGet {

    private Item m_item;
    private Double m_AmountYouGet;
    private Double m_PricePerOne;

     ProductYouGet(Item m_item, Double m_AmountYouGet, Double m_PriceToAdd) {
        this.m_item = m_item;
        this.m_AmountYouGet = m_AmountYouGet;
        this.m_PricePerOne = m_PriceToAdd;
    }

     Item getItem() {
        return m_item;
    }

     Double getAmountYouGet() {
        return m_AmountYouGet;
    }

     Double getPriceToAdd() {
        return m_PricePerOne;
    }
}
