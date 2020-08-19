package course.java.sdm.exceptions;

import java.awt.*;

public class DuplicatePointOnGridException extends RuntimeException {

    public final Point PointInput;

    public DuplicatePointOnGridException(Point pointInput) {
        PointInput = pointInput;
    }

    public DuplicatePointOnGridException(String message, Point pointInput) {
        super(message);
        PointInput = pointInput;
    }


}
