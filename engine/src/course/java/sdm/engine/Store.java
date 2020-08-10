package course.java.sdm.engine;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Store extends HasName{

    private final Point m_locationCoordinate;  //todo this need final?
    private final int m_IDNUmber;
    int m_profitFromShipping = 0;
    Map<Integer,Item> m_items = new HashMap<>();
    String m_Name;
    int PPK;

    //TODO how to implemnt price for item


    public Store(Point i_locationCoordinate,String m_Name, int i_PPK, int i_IDNumber) {
        this.m_IDNUmber = i_IDNumber;
        this.m_locationCoordinate = i_locationCoordinate;
        this.PPK=i_PPK;
        this.m_Name = m_Name;
    }

    @Override
    public String getName() {
        return m_Name;
    }

    @Override
    public void setName(String Input) {
        m_Name = Input;
    }
}
