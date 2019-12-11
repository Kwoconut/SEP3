package server;

import java.util.ArrayList;

import model.AirportGraph;
import model.Edge;
import model.Node;
import model.NodeDTO;
import model.InAirState;
import model.Plane;
import model.PlaneDTO;
import model.StaticPosition;
import model.Timer;

public class ServerModel
{
   private ArrayList<Plane> groundPlanes;
   private ArrayList<Plane> airPlanes;
   private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
   private AirportGraph airportGraph;
   private Timer timer;
   private boolean wind;

   public ServerModel()
   {
      groundPlanes = new ArrayList<Plane>();
      airPlanes = new ArrayList<Plane>();
      wind = false;
      timer = new Timer(8, 0, 0);
   }

   public void loadPlanesFromDatabase(ArrayList<Plane> planes)
   {
      for (int i = 0; i < planes.size(); i++)
      {
         if(planes.get(i).getFlightPlan().getEndLocation().equals("Aalborg"))
         {
        	 planes.get(i).setState(new InAirState());
        	 ArrayList<Node> route = new ArrayList<Node>();
        	 route.add(new Node("kkt",50,new StaticPosition(956,486)));
        	 planes.get(i).setRoute(route);
        	 planes.get(i).setSpeed(1);
        	 airPlanes.add(planes.get(i)); 
         }
         if(planes.get(i).getFlightPlan().getStartLocation().equals("Aalborg"))
         {
        	 planes.get(i).landPlane(
						getLandingNode(getWind()));
        	 groundPlanes.add(planes.get(i));
         }
      }
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

   public ArrayList<Plane> getGroundPlanes()
   {
      if (groundPlanes.size() == -1)
      {
      }
      return groundPlanes;
   }

   public ArrayList<Plane> getAirPlanes()
   {
      if (airPlanes.size() == -1)
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
      for (int i = 0; i < this.nodes.size(); i++)
      {
         nodes.add(this.nodes.get(i).convertToDTO());
      }
      return nodes;
   }

   public ArrayList<NodeDTO> getAirNodesDTO()
   {
      ArrayList<NodeDTO> nodes = new ArrayList<NodeDTO>();
      for (int i = 19; i < this.nodes.size(); i++)
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

   public void changeGroundPlaneRoute(String callSign, int startNodeId,
         int endNodeId)
   {
      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<Node> shortestDistance = airportGraph.calculateShortestDistance(
            airportGraph.getGroundNodes().get(startNodeId),
            airportGraph.getGroundNodes().get(endNodeId));

      groundPlanes.stream()
            .filter(plane -> plane.getCallSign().equals(callSign)).findFirst()
            .get().setRoute(shortestDistance);

   }

   public void changeAirPlaneRoute(String callSign, int startNodeId,
         int endNodeId)
   {
      airPlanes.stream().filter(plane -> plane.getCallSign().equals(callSign))
            .findFirst().get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<Node> shortestDistance = airportGraph.calculateShortestDistance(
            airportGraph.getGroundNodes().get(startNodeId),
            airportGraph.getGroundNodes().get(endNodeId));

      airPlanes.stream().filter(plane -> plane.getCallSign().equals(callSign))
            .findFirst().get().setRoute(shortestDistance);

   }

   public void addGroundPlane(Plane plane)
   {
      groundPlanes.add(plane);
   }

   public void addAirPlane(Plane plane)
   {
      airPlanes.add(plane);
   }

   public void incrementTimer()
   {
      timer.increment();
   }

   public Timer getTimer()
   {
      return timer;
   }

   public ArrayList<Node> getGateNodes()
   {
      ArrayList<Node> gateNodes = new ArrayList<Node>();

      for (Node nodes : this.nodes)
      {
         if (nodes.getName().contains("Gate"))
         {
            gateNodes.add(nodes);
         }
      }
      return gateNodes;
   }

   public Node getLandingNode(boolean wind)
   {
      if (wind)
      {
         return nodes.get(16);
      }
      else
      {
         return nodes.get(9);
      }
   }

   public ArrayList<Node> getTakeoffNodes()
   {
      ArrayList<Node> nodes = new ArrayList<Node>();
      nodes.add(this.nodes.get(9));
      nodes.add(this.nodes.get(16));
      return nodes;
   }

   public void reRoutePlane(String callSign, StaticPosition position)
   {
      ArrayList<Node> route = new ArrayList<Node>();
      route.add(new Node("Aalborg Airspace", 50, position));
      airPlanes.stream().filter(plane -> plane.getCallSign().equals(callSign))
            .findFirst().get().setRoute(route);
   }
}
