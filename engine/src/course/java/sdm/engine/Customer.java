package course.java.sdm.engine;
import course.java.sdm.exceptions.OrderIsNotForThisCustomerException;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Customer extends Person implements Coordinatable {

    private Point m_currentLocation;
    private final Map<Long,Order> m_OrderHistory = new HashMap<>();


    Customer(int i_IDNumber, String i_Name, Point i_currentLocation) {
        super(i_IDNumber, i_Name);
        this.m_currentLocation = i_currentLocation;
    }

    Long getIdNumber () {return super.getIDNumber();}

    void setCurrentLocation (Point i_newLocation)
    {
        this.m_currentLocation = i_newLocation;
    }

    void addOrderToHistory (Order newOrder) throws OrderIsNotForThisCustomerException {
        if (newOrder.getCustomerID() != this.getId() || m_OrderHistory.containsKey(newOrder.getOrderSerialNumber()))
            throw new OrderIsNotForThisCustomerException(newOrder.getOrderSerialNumber(),this.getId());

            m_OrderHistory.put(newOrder.getOrderSerialNumber(),newOrder);
    }


    public Map<Long, Order> getOrderHistory() {
        return m_OrderHistory;
    }

    @Override
    public Point getCoordinate() {
        return m_currentLocation;
    }

    double getAvgPriceOfShipping () {
        if (m_OrderHistory.isEmpty())
            return 0;
        return m_OrderHistory.values()
                .stream()
                .mapToDouble(v->v.getShippingPrice())
                .average().getAsDouble();
    }

    double getAvgPriceOfOrdersWithoutShipping () {

        if (m_OrderHistory.isEmpty())
            return 0;
        return m_OrderHistory.values()
                .stream()
                .mapToDouble(v->v.getItemsPrice())
                .average().getAsDouble();
    }

    int getAmountOFOrders() {
        return m_OrderHistory.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(m_currentLocation, customer.m_currentLocation) &&
                Objects.equals(m_OrderHistory, customer.m_OrderHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_currentLocation, m_OrderHistory);
    }
}
