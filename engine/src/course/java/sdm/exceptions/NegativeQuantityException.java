package course.java.sdm.exceptions;

public class NegativeQuantityException extends Exception {

    public final int Quantity;

    public NegativeQuantityException(int quantity) {
        Quantity = quantity;
    }

    public NegativeQuantityException(String message, int quantity) {
        super(message);
        Quantity = quantity;
    }
}
