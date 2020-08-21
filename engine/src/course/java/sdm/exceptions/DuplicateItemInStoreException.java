package course.java.sdm.exceptions;

public class DuplicateItemInStoreException extends Exception{

    public final long id;

    public DuplicateItemInStoreException(long id) {
        this.id = id;
    }

    public DuplicateItemInStoreException(String message, long id) {
        super(message);
        this.id = id;
    }

}
