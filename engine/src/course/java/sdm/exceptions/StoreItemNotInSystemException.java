package course.java.sdm.exceptions;

public class StoreItemNotInSystemException extends RuntimeException {

    long ItemIdInput;

    public StoreItemNotInSystemException(long id) {
        this.ItemIdInput = id;
    }

    public StoreItemNotInSystemException(String message, long id) {
        super(message);
        this.ItemIdInput = id;
    }
}
