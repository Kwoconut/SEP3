package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface GroundRadarModel
{
   ArrayList<Plane> getPlanes();

   ArrayList<GroundNode> getGroundNodes();

   void addPropertyChangeListener(PropertyChangeListener listener);


}
