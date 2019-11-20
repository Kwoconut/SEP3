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
   private ArrayList<Plane> planes;
   private ArrayList<GroundNode> groundNodes;
   private IClient client;
   private PropertyChangeSupport support = new PropertyChangeSupport(this);

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<Plane>();
      groundNodes = new ArrayList<GroundNode>();
   }

   @Override
   public void getPlaneFromServer(Plane plane)
   {
      planes.add(plane);
      System.out.println(plane);
      support.firePropertyChange("planeADD", " ", plane);
   }

   @Override
   public void setClient(Client client)
   {
      this.client = client;
   }

   @Override
   public ArrayList<Plane> getPlanes()
   {
      return planes;
   }

   @Override
   public void getGroundPlanesFromServer(ArrayList<Plane> planes) {
	   this.planes = planes;
	   
   }
   
   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      support.addPropertyChangeListener(listener);

   }

   @Override
   public ArrayList<GroundNode> getGroundNodes()
   {
      // TODO Auto-generated method stub
      return null;
   }


}
