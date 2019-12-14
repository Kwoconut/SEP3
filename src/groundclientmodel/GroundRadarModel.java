package groundclientmodel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import model.NodeDTO;
import model.PlaneDTO;

public interface GroundRadarModel
{
   void addPropertyChangeListener(PropertyChangeListener listener);

   void changePlaneRoute(String registrationNo, int startNodeId, int endNodeId);
 
}
