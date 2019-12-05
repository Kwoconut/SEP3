package model;

public interface AirTrafficControlGroundSimulator
      extends GroundRadarModel, PlaneModel, NodeModel,
      AirTrafficControlGroundSimulatorModelClientHandler
{
   void addPlane(PlaneDTO plane);

   void removePlane(int i);
}
