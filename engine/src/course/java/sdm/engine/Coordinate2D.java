package course.java.sdm.engine;
import java.awt.*;



public class Coordinate2D extends Point {


    public Coordinate2D(int xInput, int yInput) {
        if (xInput > SuperDuperMarketSystem.MAX_COORDINATE || yInput > SuperDuperMarketSystem.MAX_COORDINATE || yInput < 1 || xInput <1)
            throw (new IndexOutOfBoundsException("Coordinate x and y needs to be between 1 - "+SuperDuperMarketSystem.MAX_COORDINATE+", but got x = (",xInput+" ,y = "+yInput));

        super.x = xInput;
        super.y = yInput;
    }
        //TODO in system there is a map of cordinate, key= point in map to store (or customer?)

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    //todo need to overide distance with exption?
}
