package model;

public interface AirTrafficControlGroundSimulator
      extends GroundRadarModel, PlaneModel, GroundNodeModel,
      AirTrafficControlGroundSimulatorModelClientHandler
{

   void addPlane(PlaneDTO plane);

   void removePlane(int i);

}
