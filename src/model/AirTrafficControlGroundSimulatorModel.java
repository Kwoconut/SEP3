package model;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;
import client.IClient;

public class AirTrafficControlGroundSimulatorModel
      implements AirTrafficControlGroundSimulatorModelClientHandler,
      AirTrafficControlGroundSimulator, Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 3218559717712719996L;
   private ArrayList<Plane> planes;
   private AirportGraph airportGraph;
   private IClient client;

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<Plane>();
      planes.add(new Plane("WZ2345", "Boeing 747", "WizzAir", "Air", null,
            new Position(80, 150)));
      airportGraph = new AirportGraph();
   }

   @Override
   public void getPlanesFromServer(ArrayList<Plane> planes)
   {
      this.planes = planes;
      System.out.println("Client got plane list from server");
   }

   @Override
   public void setClient(Client client)
   {
      this.client = client;
   }

   public AirportGraph getAirportGraph()
   {
      return airportGraph;
   }

   @Override
   public ArrayList<Plane> getPlanes()
   {
      return planes;
   }

   @Override
   public void addPlane(Plane plane)
   {
      planes.add(plane);
   }

   @Override
   public ArrayList<GroundNode> getGroundNodes()
   {
      return airportGraph.getGroundNodes();
   }

   public int[] movePlane(int startLocation, int endLocation)
   {
      ArrayList<GroundNode> shortestGroundPath = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startLocation),
                  airportGraph.getGroundNodes().get(endLocation));

      int[] shortestPath = new int[shortestGroundPath.size()];

      for (int i = 0; i < shortestPath.length; i++)
      {
         shortestPath[i] = shortestGroundPath.get(i).getNodeId();
      }

      return shortestPath;
   }

}
