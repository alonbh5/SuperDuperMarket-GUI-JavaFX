package course.java.sdm.engine;
import java.awt.*;



public class Coordinate2D extends Point {

    public static final int MAX_COORDINATE = 50;

    public Coordinate2D(int xInput, int yInput) {
        if (xInput > MAX_COORDINATE || yInput > MAX_COORDINATE || yInput < 1 || xInput <1)
            throw (new IndexOutOfBoundsException("Coordinate x and y needs to be between 1 - "+MAX_COORDINATE+", but got x = (",xInput+" ,y = "+yInput));

        super.x = xInput;
        super.y = yInput;
    }
        //TODO in system there is a map of cordinate, key= point in map to store (or customer?)

}
