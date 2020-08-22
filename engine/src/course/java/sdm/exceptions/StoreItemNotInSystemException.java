package course.java.sdm.exceptions;

public class StoreItemNotInSystemException extends Exception {

    public final long ItemIdInput;
    public final long StoreIdInput;

    public StoreItemNotInSystemException(long itemIdInput, long storeIdInput) {
        ItemIdInput = itemIdInput;
        StoreIdInput = storeIdInput;
    }

    public StoreItemNotInSystemException(String message, long itemIdInput, long storeIdInput) {
        super(message);
        ItemIdInput = itemIdInput;
        StoreIdInput = storeIdInput;
    }
}
