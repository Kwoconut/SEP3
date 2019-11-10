package model;

import java.util.ArrayList;

public interface GroundRadarModel
{
   ArrayList<Plane> getPlanes();

   ArrayList<GroundNode> getGroundNodes();

   int[] movePlane(int startLocation, int endLocation);

}
