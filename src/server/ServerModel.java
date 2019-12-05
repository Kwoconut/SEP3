package server;

import java.util.ArrayList;

import model.AirportGraph;
import model.Edge;
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
   private ArrayList<GroundNode> nodes;
   private ArrayList<Edge> edges;
   private AirportGraph airportGraph;
   private boolean wind;

   public ServerModel()
   {
      planes = new ArrayList<Plane>();
      groundPlanes = new ArrayList<Plane>();
      wind = false;
   }
   
   public AirportGraph getGraph()
   {
      return airportGraph;
   }

   public void loadPlanesFromDatabase(ArrayList<Plane> planes)
   {
      for (int i = 0; i < planes.size(); i++)
      {
         planes.get(i).setState(new InAirState());

      }
      this.planes = planes;
   }
   
   public void changeWind()
   {
      wind = !wind;
   }
   
   public boolean getWind()
   {
      return wind;
   }

   public void loadEdgesFromDatabase(ArrayList<Edge> edges)
   {
      this.edges = edges;
   }

   public void loadGroundNodesFromDatabase(ArrayList<GroundNode> groundNodes)
   {
      this.nodes = groundNodes;
      airportGraph = new AirportGraph(groundNodes);

      for (GroundNode node : nodes)
      {
         node.setShortestPath(new ArrayList<GroundNode>());
         node.setJointEdges(new ArrayList<Edge>());
      }
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
      for (int i = 0; i < this.nodes.size(); i++)
      {
         nodes.add(this.nodes.get(i).convertToDTO());
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
      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<GroundNode> shortestDistance = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startNodeId),
                  airportGraph.getGroundNodes().get(endNodeId));


      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().setRoute(shortestDistance);

   }

}
