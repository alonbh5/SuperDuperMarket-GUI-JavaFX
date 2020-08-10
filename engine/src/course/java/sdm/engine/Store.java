package course.java.sdm.engine;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Store implements HasName{

    private final Coordinate2D m_locationCoordinate;  //todo this need final?
    private final int m_IDNUmber;
    int m_profitFromShipping = 0;
    Map<Integer,Item> m_itemsן = new HashMap<>();
    Map<Integer,Double> m_itemsPricesן = new HashMap<>();
    String m_Name;
    int PPK;

    //TODO how to implemnt price for item


    public Store(Coordinate2D i_locationCoordinate,String m_Name, int i_PPK, int i_IDNumber) {
        this.m_IDNUmber = i_IDNumber;
        this.m_locationCoordinate = i_locationCoordinate;
        this.PPK=i_PPK;
        this.m_Name = m_Name;
    }

    public boolean addItemToStore (Item itemToAdd, double Price)
    {
        boolean res;
        Integer itemKey = itemToAdd.getSerialNumber();

        if (m_itemsן.containsKey(itemKey))

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
