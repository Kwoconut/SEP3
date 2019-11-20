package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface GroundRadarModel
{
   ArrayList<PlaneDTO> getPlanes();

   ArrayList<GroundNode> getGroundNodes();

   void addPropertyChangeListener(PropertyChangeListener listener);


}
