package course.java.sdm.engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SuperDuperMarketSystem {
    //todo accses modifer this is the only public!

    public static final int MAX_COORDINATE = 50;
    public static final int MIN_COORDINATE = 1;

    List<Item> m_ItemsInSystem = new ArrayList<Item>();
    Map<Point,Coordinatable> m_SystemGrid = new HashMap<>(); //all the shops
    Map<Integer,Order> m_OrderHistory = new HashMap<>(); //all the shops

}
