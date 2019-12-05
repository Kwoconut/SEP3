package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface GroundRadarModel
{
   ArrayList<PlaneDTO> getPlanes();

   ArrayList<GroundNodeDTO> getGroundNodes();

   void addPropertyChangeListener(PropertyChangeListener listener);

   void changePlaneRoute(String callSign, int startNodeId, int endNodeId);
   
   boolean getWind();

}
