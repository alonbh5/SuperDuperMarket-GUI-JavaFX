package course.java.sdm.exceptions;

public class ItemIsNotSoldAtAllException extends RuntimeException {

    public final long ItemID;
    public final String ItemName;

    public ItemIsNotSoldAtAllException(long itemID, String itemName) {
        ItemID = itemID;
        ItemName = itemName;
    }

    public ItemIsNotSoldAtAllException(String message, long itemID, String itemName) {
        super(message);
        ItemID = itemID;
        ItemName = itemName;
    }
}
