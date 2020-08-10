package course.java.sdm.exceptions;

public class NegativePrice extends RuntimeException {

    public double PriceReceived;

    public NegativePrice(double i_PriceReceived) {
        super("Negative Number of "+i_PriceReceived+" was sent for pricing");
        this.PriceReceived = i_PriceReceived;
    }

    public NegativePrice(String message, double i_PriceReceived) {
        super(message);
        this.PriceReceived = i_PriceReceived;
    }
}
