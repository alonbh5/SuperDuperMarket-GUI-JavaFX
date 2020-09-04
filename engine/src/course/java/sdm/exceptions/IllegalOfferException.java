package course.java.sdm.exceptions;

public class IllegalOfferException extends Exception {

    public final String OfferName;

    public IllegalOfferException(String offerName) {
        OfferName = offerName;
    }

    public IllegalOfferException(String message, String offerName) {
        super(message);
        OfferName = offerName;
    }
}
