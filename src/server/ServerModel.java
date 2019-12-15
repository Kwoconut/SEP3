package server;

import java.util.ArrayList;

import model.PathfindingGraph;
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
   private ArrayList<Plane> simulationGroundPlanes;
   private ArrayList<Plane> simulationAirPlanes;
   private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
   private PathfindingGraph airportGraph;
   private PathfindingGraph airspaceGraph;
   private Timer timer;
   private boolean wind;

   public ServerModel()
   {
      groundPlanes = new ArrayList<Plane>();
      airPlanes = new ArrayList<Plane>();
      simulationGroundPlanes = new ArrayList<Plane>();
      simulationAirPlanes = new ArrayList<Plane>();
      wind = false;
      timer = new Timer(0, 0, 8);
   }

   public void loadPlanesFromDatabase(ArrayList<Plane> planes)
   {
      for (int i = 0; i < planes.size(); i++)
      {
         if (planes.get(i).getFlightPlan().getEndLocation().equals("Aalborg"))
         {
            planes.get(i).getPosition()
                  .setPosition(getApproachNodesByDirection(
                        planes.get(i).getFlightPlan().getStartLocation())
                              .getPosition());
            ArrayList<Node> route = new ArrayList<Node>();
            if (wind)
            {
               Node approachNode = nodes.stream()
                     .filter(node -> node.getNodeId() == 29).findFirst().get();
               route.add(
                     new Node(approachNode.getName(), approachNode.getNodeId(),
                           new StaticPosition(
                                 approachNode.getPosition().getXCoordinate(),
                                 approachNode.getPosition().getYCoordinate())));

            }
            else
            {
               Node approachNode = nodes.stream()
                     .filter(node -> node.getNodeId() == 25).findFirst().get();
               route.add(
                     new Node(approachNode.getName(), approachNode.getNodeId(),
                           new StaticPosition(
                                 approachNode.getPosition().getXCoordinate(),
                                 approachNode.getPosition().getYCoordinate())));

            }
            route.add(nodes.stream().filter(node -> node.getNodeId() == 22)
                  .findFirst().get());
            planes.get(i).approachPlane(route);
            ;
            airPlanes.add(planes.get(i));
         }
         if (planes.get(i).getFlightPlan().getStartLocation().equals("Aalborg"))
         {
            planes.get(i).landPlane(getLandingNode(getWind()));
            groundPlanes.add(planes.get(i));
         }
      }
   }

   public void loadNodesFromDatabase(ArrayList<Node> nodes)
   {
      this.nodes = nodes;
      airportGraph = new PathfindingGraph(getGroundNodes());
      airspaceGraph = new PathfindingGraph(getAirNodes());

      for (Node node : nodes)
      {
         node.setShortestPath(new ArrayList<Node>());
         node.setJointEdges(new ArrayList<Edge>());
      }
   }

   public ArrayList<Node> getGroundNodes()
   {
      ArrayList<Node> groundNodes = new ArrayList<Node>();
      for (int i = 0; i < nodes.size(); i++)
      {
         if (nodes.get(i).IsGroundNode())
         {
            groundNodes.add(nodes.get(i));
         }
      }
      return groundNodes;
   }

   public ArrayList<Node> getAirNodes()
   {
      ArrayList<Node> airNodes = new ArrayList<Node>();
      for (int i = 0; i < nodes.size(); i++)
      {
         if (!nodes.get(i).IsGroundNode())
         {
            airNodes.add(nodes.get(i));
         }
      }
      return airNodes;
   }

   public void loadEdgesFromDatabase(ArrayList<Edge> edges)
   {
      this.edges = edges;
   }

   public ArrayList<Plane> getGroundPlanes()
   {
      return groundPlanes;
   }

   public ArrayList<Plane> getAirPlanes()
   {
      return airPlanes;
   }

   public ArrayList<Plane> getSimulationGroundPlanes()
   {
      return simulationGroundPlanes;
   }

   public ArrayList<Plane> getSimulationAirPlanes()
   {
      return simulationAirPlanes;
   }

   public ArrayList<PlaneDTO> getSimulationGroundPlanesDTO()
   {

      ArrayList<PlaneDTO> planesToSend = new ArrayList<PlaneDTO>();
      for (int i = 0; i < simulationGroundPlanes.size(); i++)
      {
         planesToSend.add(simulationGroundPlanes.get(i).convertToDTO());
      }
      return planesToSend;
   }

   public ArrayList<PlaneDTO> getSimulationAirPlanesDTO()
   {
      ArrayList<PlaneDTO> planesToSend = new ArrayList<PlaneDTO>();
      for (int i = 0; i < simulationAirPlanes.size(); i++)
      {
         planesToSend.add(simulationAirPlanes.get(i).convertToDTO());
      }
      return planesToSend;
   }

   public ArrayList<NodeDTO> getGroundNodesDTO()
   {
      ArrayList<NodeDTO> nodes = new ArrayList<NodeDTO>();
      for (int i = 0; i < this.nodes.size(); i++)
      {
         if (this.nodes.get(i).IsGroundNode())
         {
            nodes.add(this.nodes.get(i).convertToDTO());
         }
      }
      return nodes;
   }

   public ArrayList<NodeDTO> getAirNodesDTO()
   {
      ArrayList<NodeDTO> nodes = new ArrayList<NodeDTO>();
      for (int i = 19; i < this.nodes.size(); i++)
      {
         if (!this.nodes.get(i).IsGroundNode())
         {
            nodes.add(this.nodes.get(i).convertToDTO());
         }
      }
      return nodes;
   }

   public PathfindingGraph getGraph()
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

   public void changeGroundPlaneRoute(String registrationNo, int startNodeId,
         int endNodeId)
   {
      simulationGroundPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(registrationNo))
            .findFirst().get().stopPlane();

      airportGraph.generateAirportGraph(getGroundNodes(), edges);

      ArrayList<Node> shortestDistance = airportGraph.calculateShortestDistance(
            airportGraph.getGroundNodes().get(startNodeId),
            airportGraph.getGroundNodes().get(endNodeId));

      simulationGroundPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(registrationNo))
            .findFirst().get().setRoute(shortestDistance);

   }

   public void changeAirPlaneRoute(String registrationNo, int startNodeId,
         int endNodeId)
   {
      airPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(registrationNo))
            .findFirst().get().stopPlane();

      airportGraph.generateAirportGraph(nodes, edges);

      ArrayList<Node> shortestDistance = airportGraph.calculateShortestDistance(
            airportGraph.getGroundNodes().get(startNodeId),
            airportGraph.getGroundNodes().get(endNodeId));

      airPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(registrationNo))
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

   public void reRoutePlane(String callSign, StaticPosition position)
   {
      ArrayList<Node> route = new ArrayList<Node>();
      StaticPosition planePosition = airPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(callSign))
            .findFirst().get().getPosition();
      double xCoordinateLine = planePosition.getXCoordinate()
            - position.getXCoordinate();
      double yCoordinateLine = planePosition.getYCoordinate()
            - position.getYCoordinate();
      route.add(new Node("Aalborg Airspace", 50, position));
      for (int i = 0; i < 15; i++)
      {
         route.add(new Node("Aalborg Airpsace", 50, new StaticPosition(
               route.get(i).getPosition().getXCoordinate() - xCoordinateLine,
               route.get(i).getPosition().getYCoordinate() - yCoordinateLine)));
      }
      airPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(callSign))
            .findFirst().get().setRoute(route);
   }

   public Node getApproachNodesByDirection(String cityName)
   {
      System.out.println(cityName);
      return this.nodes.stream().filter(
            node -> !node.IsGroundNode() && node.getName().contains(cityName))
            .findFirst().get();
   }

   public void stopPlane(String registrationNo)
   {
      this.simulationGroundPlanes.stream()
            .filter(plane -> plane.getRegistrationNo().equals(registrationNo))
            .findFirst().get().stopPlane();
   }
}
