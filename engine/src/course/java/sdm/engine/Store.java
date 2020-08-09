package course.java.sdm.engine;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Store extends HasName{

    private Point m_loctionCoordinate;
    Map<Integer,Item> m_items = new HashMap<>();
    String m_Name;
    int PPK;

    //TODO how to implemnt price for item

    @Override
    public String getName() {
        return m_Name;
    }

    @Override
    public void setName(String Input) {
        m_Name = Input;
    }
}
