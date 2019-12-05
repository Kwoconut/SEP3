package server;

import java.util.ArrayList;

import model.AirportGraph;
import model.Edge;
import model.Node;
import model.NodeDTO;
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
   private ArrayList<Plane> airPlanes;
   private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
   private AirportGraph airportGraph;
   private boolean wind;

   public ServerModel()
   {
      planes = new ArrayList<Plane>();
      groundPlanes = new ArrayList<Plane>();
      airPlanes= new ArrayList<Plane>();
      wind = false;
   }
   
   public void loadPlanesFromDatabase(ArrayList<Plane> planes)
   {
	   for (int i = 0; i < planes.size(); i++)
	   {
		   planes.get(i).setState(new InAirState());
		   
	   }
	   this.planes = planes;
   }

   public void loadNodesFromDatabase(ArrayList<Node> nodes)
   {
      this.nodes = nodes;
      airportGraph = new AirportGraph(nodes);

      for (Node node : nodes)
      {
         node.setShortestPath(new ArrayList<Node>());
         node.setJointEdges(new ArrayList<Edge>());
      }
   }
   
   public void loadEdgesFromDatabase(ArrayList<Edge> edges)
   {
	   this.edges = edges;
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
   
   private ArrayList<Plane> getAirPlanes()
   {
	   if(airPlanes.size() == -1)
	   {
	   }
	   return airPlanes;
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
   
   public ArrayList<PlaneDTO> getAirPlanesDTO()
   {
      if (airPlanes.size() == -1)
      {
      }
      ArrayList<PlaneDTO> planesToSend = new ArrayList<PlaneDTO>();
      for (int i = 0; i < airPlanes.size(); i++)
      {
         planesToSend.add(airPlanes.get(i).convertToDTO());
      }
      return planesToSend;
   }
   
   public ArrayList<NodeDTO> getGroundNodesDTO()
   {
	   ArrayList<NodeDTO> nodes = new ArrayList<NodeDTO>();
	   for (int i = 0; i < 19; i++)
	   {
		   nodes.add(this.nodes.get(i).convertToDTO());
	   }
	   return nodes;
   }
   
   public ArrayList<NodeDTO> getAirNodesDTO()
   {
	   ArrayList<NodeDTO> nodes = new ArrayList<NodeDTO>();
	   for (int i = 19; i < 22; i++)
	   {
		   nodes.add(this.nodes.get(i).convertToDTO());
	   }
	   return nodes;
   }
    
   public AirportGraph getGraph()
   {
      return airportGraph;
   }
   
   public boolean getWind()
   {
      return wind;
   }
   
   public void changeWind()
   {
	   wind = !wind;
   }

   public void changeGroundPlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<Node> shortestDistance = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startNodeId),
                  airportGraph.getGroundNodes().get(endNodeId));


      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().setRoute(shortestDistance);

   }
   
   public void changeAirPlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      airPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<Node> shortestDistance = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startNodeId),
                  airportGraph.getGroundNodes().get(endNodeId));


      airPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().setRoute(shortestDistance);

   }

   public void addGroundPlane(Plane plane)
   {
	   groundPlanes.add(plane);
   }
   
   public void addAirPlane(Plane plane)
   {
	   airPlanes.add(plane);
   }
}
