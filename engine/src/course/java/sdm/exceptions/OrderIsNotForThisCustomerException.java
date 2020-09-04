package course.java.sdm.exceptions;

public class OrderIsNotForThisCustomerException extends Exception {

     private final Long OrderID;
     private final Long UserID;

    public OrderIsNotForThisCustomerException(Long orderID, Long userID) {
        OrderID = orderID;
        UserID = userID;
    }

    public OrderIsNotForThisCustomerException(String message, Long orderID, Long userID) {
        super(message);
        OrderID = orderID;
        UserID = userID;
    }

    public Long getOrderID() {
        return OrderID;
    }

    public Long getUserID() {
        return UserID;
    }
}
