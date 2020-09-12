package course.java.sdm.exceptions;

public class ItemIsTheOnlyOneInStoreException extends Exception {
    public final long ItemID;

    public ItemIsTheOnlyOneInStoreException(long itemID) {
        ItemID = itemID;
    }

    public ItemIsTheOnlyOneInStoreException(String message, long itemID) {
        super(message);
        ItemID = itemID;
    }
}
