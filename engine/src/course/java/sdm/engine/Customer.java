package course.java.sdm.engine;

import java.awt.*;

public class Customer extends Person implements Coordinatable {

    private Point m_currentLocation;

    public Customer(int i_IDNumber, String i_Name, Point i_currentLocation) {
        super(i_IDNumber, i_Name);
        this.m_currentLocation = i_currentLocation;
    }

    public void setCurrentLocation (Point i_newLocation)
    {
        this.m_currentLocation = i_newLocation;
    }

    @Override
    public Point getCoordinate() {
        return m_currentLocation;
    }
}
