package course.java.sdm.exceptions;

public class NegativePrice extends RuntimeException {

    public int PriceReceived;

    public NegativePrice(int i_PriceReceived) {
        super("Negtive Number of "+i_PriceReceived+" was sent for pricing");
        this.PriceReceived = i_PriceReceived;
    }

    public NegativePrice(String message, int i_PriceReceived) {
        super(message);
        this.PriceReceived = i_PriceReceived;
    }
}
