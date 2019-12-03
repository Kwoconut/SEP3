package server;

import java.util.ArrayList;

import model.AirportGraph;
import model.GroundNode;
import model.GroundNodeDTO;
import model.InAirState;
import model.LandedState;
import model.LandingState;
import model.Plane;
import model.PlaneDTO;
import model.StaticPosition;

public class ServerModel
{
   private ArrayList<Plane> planes;
   private ArrayList<Plane> groundPlanes;
   private AirportGraph airportGraph;

   public ServerModel()
   {
      planes = new ArrayList<Plane>();
      groundPlanes = new ArrayList<Plane>();
   }

   public void loadPlanesFromDatabase(ArrayList<Plane> planes)
   {
      for (int i = 0; i < planes.size(); i++)
      {
         planes.get(i).setState(new InAirState());

      }
      this.planes = planes;
   }

   public void loadGroundNodesFromDatabase(ArrayList<GroundNode> groundNodes)
   {
      airportGraph = new AirportGraph();
   }

   public ArrayList<Plane> getPlanes()
   {
      return planes;
   }

   public ArrayList<Plane> getGroundPlanes()
   {
      if (groundPlanes.size() == -1)
      {
      }
      return groundPlanes;
   }

   public ArrayList<GroundNodeDTO> getGroundNodesDTO()
   {
      ArrayList<GroundNodeDTO> nodes = new ArrayList<GroundNodeDTO>();
      for (int i = 0; i < airportGraph.getGroundNodes().size(); i++)
      {
         nodes.add(airportGraph.getGroundNodes().get(i).convertToDTO());
      }
      return nodes;
   }

   public ArrayList<PlaneDTO> getGroundPlanesDTO()
   {
      if (groundPlanes.size() == -1)
      {
      }
      ArrayList<PlaneDTO> planesToSend = new ArrayList<PlaneDTO>();
      for (int i = 0; i < groundPlanes.size(); i++)
      {
         planesToSend.add(groundPlanes.get(i).convertToDTO());
      }
      return planesToSend;
   }

   public void addGroundPlane(Plane plane)
   {
      groundPlanes.add(plane);
   }

   public void changePlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      
      System.out.println(startNodeId);
      System.out.println(endNodeId);

      ArrayList<GroundNode> shortestDistance = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startNodeId),
                  airportGraph.getGroundNodes().get(endNodeId));

      System.out.println(shortestDistance);
      
      ArrayList<StaticPosition> route = new ArrayList<StaticPosition>();
      for (int i = 0; i < shortestDistance.size(); i++)
      {
         route.add(shortestDistance.get(i).getPosition());
      }
      
      System.out.println(route);

      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().setRoute(route);

   }

}
