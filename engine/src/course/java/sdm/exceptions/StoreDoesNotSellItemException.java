package course.java.sdm.exceptions;

public class StoreDoesNotSellItemException extends RuntimeException {

    public final long StoreID;

    public StoreDoesNotSellItemException(long storeID) {
        StoreID = storeID;
    }

    public StoreDoesNotSellItemException(String message, long storeID) {
        super(message);
        StoreID = storeID;
    }
}
