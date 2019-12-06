package groundclientmodel;

import model.NodeModel;
import model.PlaneDTO;
import model.PlaneModel;

public interface AirTrafficControlGroundSimulator
      extends GroundRadarModel, PlaneModel, NodeModel,
      AirTrafficControlGroundSimulatorModelClientHandler
{
   void addPlane(PlaneDTO plane);

   void removePlane(int i);
}
