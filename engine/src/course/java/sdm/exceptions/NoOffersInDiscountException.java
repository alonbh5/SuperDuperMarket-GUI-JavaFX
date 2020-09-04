package course.java.sdm.exceptions;

public class NoOffersInDiscountException extends Exception {

    public final String DiscountName;

    public NoOffersInDiscountException(String discountName) {
        DiscountName = discountName;
    }

    public NoOffersInDiscountException(String message, String discountName) {
        super(message);
        DiscountName = discountName;
    }
}
