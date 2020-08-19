package course.java.sdm.exceptions;

public class DuplicateStoreInSystemException extends RuntimeException {

    public final long id;

    public DuplicateStoreInSystemException(long id) {
        this.id = id;
    }

    public DuplicateStoreInSystemException(String message, long id) {
        super(message);
        this.id = id;
    }
}
