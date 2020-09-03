package course.java.sdm.exceptions;

public class DuplicateCustomerInSystemException extends Exception {

    public final long id;

    public DuplicateCustomerInSystemException(long id) {
        this.id = id;
    }

    public DuplicateCustomerInSystemException(String message, long id) {
        super(message);
        this.id = id;
    }
}
