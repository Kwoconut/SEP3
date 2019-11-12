package model;

import java.beans.PropertyChangeListener;

public interface AirTrafficControlGroundSimulator
      extends GroundRadarModel, PlaneModel, GroundNodeModel
{
   void addPlane(Plane plane);

}
