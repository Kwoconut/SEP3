package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

import client.Client;
import client.IClient;
import javafx.application.Platform;

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
   private PropertyChangeSupport support = new PropertyChangeSupport(this);

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<Plane>();
      airportGraph = new AirportGraph();
   }

   @Override
   public void getPlanesFromServer(ArrayList<Plane> planes)
   {
      this.planes = planes;
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
      support.firePropertyChange("planeADD", " ", plane);
   }

   @Override
   public ArrayList<GroundNode> getGroundNodes()
   {
      return airportGraph.getGroundNodes();
   }

   public double[] movePlane(int startLocation, int endLocation)
   {
      ArrayList<GroundNode> shortestGroundPath = airportGraph
            .calculateShortestDistance(
                  airportGraph.getGroundNodes().get(startLocation),
                  airportGraph.getGroundNodes().get(endLocation));

      double[] shortestPath = new double[shortestGroundPath.size() * 2];

      int j = 0;
      for (int i = 0; i < shortestGroundPath.size(); i++)
      {
         shortestPath[j] = shortestGroundPath.get(i).getPosition()
               .getXCoordinate();
         shortestPath[j + 1] = shortestGroundPath.get(i).getPosition()
               .getYCoordinate();
         j = j + 2;
      }

      return shortestPath;
   }

   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      support.addPropertyChangeListener(listener);

   }

}
