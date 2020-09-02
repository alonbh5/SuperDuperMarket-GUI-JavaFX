package course.java.sdm.engine;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class Customer extends Person implements Coordinatable {

    private Point m_currentLocation;
    private Map<Long,Order> m_OrderHistory = new HashMap<>();


    Customer(int i_IDNumber, String i_Name, Point i_currentLocation) {
        super(i_IDNumber, i_Name);
        this.m_currentLocation = i_currentLocation;
    }

    Long getIdNumber () {return super.getIDNumber();};

    void setCurrentLocation (Point i_newLocation)
    {
        this.m_currentLocation = i_newLocation;
    }

    void addOrderToHistory (Order newOrder)
    {
        if (m_OrderHistory.getCustmerID() != this.m_IDNumber || m_OrderHistory.containsKey(newOrder.getOrderSerialNumber()))
            throw new OrderIsNotForThisCustomer();

            m_OrderHistory.put(newOrder.getOrderSerialNumber(),newOrder);
    }


    public Map<Long, Order> getOrderHistory() {
        return m_OrderHistory;
    }

    @Override
    public Point getCoordinate() {
        return m_currentLocation;
    }
}
