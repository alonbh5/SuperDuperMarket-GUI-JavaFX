package course.java.sdm.exceptions;

public class CustomerNotInSystemException extends Exception {

    public final long CustomerID;

    public CustomerNotInSystemException(long customerID) {
        CustomerID = customerID;
    }

    public CustomerNotInSystemException(String message, long customerID) {
        super(message);
        CustomerID = customerID;
    }
}
