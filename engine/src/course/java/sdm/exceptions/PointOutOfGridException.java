package course.java.sdm.exceptions;

import java.awt.*;

public class PointOutOfGridException extends Exception{

    public final Point PointReceived;

    public PointOutOfGridException(Point pointReceived) {
        super("Out of Grid Point Received - "+pointReceived);
        PointReceived = pointReceived;
    }

    public PointOutOfGridException(String message, Point pointReceived) {
        super(message);
        PointReceived = pointReceived;
    }
}
