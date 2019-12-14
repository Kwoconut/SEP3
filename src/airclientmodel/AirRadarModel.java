package airclientmodel;

import java.beans.PropertyChangeListener;

import model.StaticPosition;

public interface AirRadarModel
{
   void addPropertyChangeListener(PropertyChangeListener listener);

   void reRoutePlane(String registrationNo, StaticPosition position);

}
