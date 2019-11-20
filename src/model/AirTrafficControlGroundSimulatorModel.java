package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

import client.Client;
import client.IClient;

public class AirTrafficControlGroundSimulatorModel
      implements AirTrafficControlGroundSimulator, Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 3218559717712719996L;
   private ArrayList<PlaneDTO> planes;
   private ArrayList<GroundNodeDTO> groundNodes;
   private IClient client;
   private PropertyChangeSupport support = new PropertyChangeSupport(this);

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<PlaneDTO>();
      groundNodes = new ArrayList<GroundNodeDTO>();
   }

   @Override
   public void getPlaneDTOFromServer(PlaneDTO plane)
   {
      planes.add(plane);
      support.firePropertyChange("planeADD", " ", plane);
   }

   @Override
   public void setClient(Client client)
   {
      this.client = client;
   }

   public void changePlaneRoute(String callSign, int startNodeId, int endNodeId)
   {

   }

   @Override
   public ArrayList<PlaneDTO> getPlanes()
   {
      return planes;
   }

   @Override
   public void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes)
   {
      this.planes = planes;

   }

   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      support.addPropertyChangeListener(listener);

   }

   @Override
   public ArrayList<GroundNodeDTO> getGroundNodes()
   {
      return groundNodes;
   }
   
   public void addPlane(PlaneDTO plane)
   {
      this.planes.add(plane);
      support.firePropertyChange("planeADD", " ", plane);

   }
   
   public void removePlane(int index)
   {
      this.planes.remove(index);
      System.out.println(index);
      support.firePropertyChange("planeREMOVE"," ",index);
   }

}
