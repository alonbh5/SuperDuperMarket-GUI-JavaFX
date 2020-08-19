package course.java.sdm.exceptions;

public class NegativePriceException extends RuntimeException {

    public final double PriceReceived;

    public NegativePriceException(double i_PriceReceived) {
        super("Negative Number of "+i_PriceReceived+" was sent for pricing");
        this.PriceReceived = i_PriceReceived;
    }

    public NegativePriceException(String message, double i_PriceReceived) {
        super(message);
        this.PriceReceived = i_PriceReceived;
    }
}
