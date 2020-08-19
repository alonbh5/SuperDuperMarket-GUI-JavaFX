package course.java.sdm.exceptions;

public class WrongPayingMethodException extends RuntimeException{

    public final String PayingInput;

    public WrongPayingMethodException(String payingInput) {
        PayingInput = payingInput;
    }

    public WrongPayingMethodException(String message, String payingInput) {
        super(message);
        PayingInput = payingInput;
    }
}
