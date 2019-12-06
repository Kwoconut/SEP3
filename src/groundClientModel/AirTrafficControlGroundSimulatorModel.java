package groundClientModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import groundclient.GroundClient;
import groundclient.GroundIClient;
import model.NodeDTO;
import model.PlaneDTO;
import model.Timer;

public class AirTrafficControlGroundSimulatorModel
      implements AirTrafficControlGroundSimulator, Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 3218559717712719996L;
   private ArrayList<PlaneDTO> planes;
   private ArrayList<NodeDTO> groundNodes;
   private boolean wind;
   private GroundIClient client;
   private PropertyChangeSupport support = new PropertyChangeSupport(this);

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<PlaneDTO>();
      groundNodes = new ArrayList<NodeDTO>();
      wind = false;
   }

   @Override
   public void getPlaneDTOFromServer(PlaneDTO plane)
   {
      planes.add(plane);
      support.firePropertyChange("planeADD", " ", plane);
   }

   @Override
   public void setClient(GroundClient groundClient)
   {
      this.client = groundClient;
   }

   public void changePlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      try
      {
         client.changeGroundPlaneRoute(callSign, startNodeId, endNodeId);
      }
      catch (RemoteException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Override
   public ArrayList<PlaneDTO> getPlanes()
   {
      return planes;
   }

   @Override
   public void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes)
   {
      // this.planes = planes;
      support.firePropertyChange("positionUPDATE", " ", planes);

   }

   @Override
   public void addPropertyChangeListener(PropertyChangeListener listener)
   {
      support.addPropertyChangeListener(listener);

   }

   @Override
   public ArrayList<NodeDTO> getGroundNodes()
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
      support.firePropertyChange("planeREMOVE", " ", index);
   }

   @Override
   public void simulationFailed()
   {
      support.firePropertyChange("simulationFAILED", " ", true);

   }

   @Override
   public void getGroundNodesDTOFromServer(ArrayList<NodeDTO> nodes)
   {
      this.groundNodes = nodes;
      support.firePropertyChange("nodeADD", " ", nodes);
   }

   @Override
   public boolean getWind()
   {
      return wind;
   }

   @Override
   public void getWindFromServer(boolean wind)
   {
      this.wind = wind;
      support.firePropertyChange("windADD"," ",wind);
   }

   @Override
   public void getTimerFromServer(Timer timer)
   {
      support.firePropertyChange("timerUPDATE"," ",timer);
      System.out.println(timer);
      
   }
}
