package model;

public interface AirTrafficControlGroundSimulator
      extends GroundRadarModel, PlaneModel, GroundNodeModel
{
   void addPlane(Plane plane);

}
