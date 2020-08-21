package course.java.sdm.exceptions;

public class DuplicateStoreInSystemException extends Exception {

    public final long Storeid;

    public DuplicateStoreInSystemException(long id) {
        this.Storeid = id;
    }

    public DuplicateStoreInSystemException(String message, long id) {
        super(message);
        this.Storeid = id;
    }
}
