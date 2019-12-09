package airclientviewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import airclientmodel.AirNodeModel;
import airclientmodel.AirPlaneModel;
import airclientmodel.AirRadarModel;
import groundclientmodel.GroundRadarModel;
import groundclientmodel.GroundNodeModel;
import groundclientmodel.GroundPlaneModel;
import groundclientviewmodel.GroundNodeViewModel;
import groundclientviewmodel.GroundPlaneViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BoardingState;
import model.EmergencyState;
import model.NodeDTO;
import model.PlaneDTO;
import model.StaticPosition;
import model.Timer;

public class AirRadarViewModel implements PropertyChangeListener
{
   private ObservableList<AirPlaneViewModel> planes;
   private ObservableList<AirNodeViewModel> airNodes;
   private ObjectProperty<AirPlaneViewModel> selectedPlane;
   private ObjectProperty<AirNodeViewModel> selectedNode;
   private AirRadarModel model;
   private AirNodeModel airNodeViewModel;
   private AirPlaneModel planeViewModel;
   private BooleanProperty simulationFailed;
   private BooleanProperty windProperty;
   private StringProperty timerProperty;

   public AirRadarViewModel(AirRadarModel model, AirNodeModel airNodeViewModel,
         AirPlaneModel planeViewModel)
   {
      this.model = model;
      this.airNodeViewModel = airNodeViewModel;
      this.planeViewModel = planeViewModel;
      this.planes = FXCollections.observableArrayList();
      this.airNodes = FXCollections.observableArrayList();
      this.simulationFailed = new SimpleBooleanProperty(false);
      this.windProperty = new SimpleBooleanProperty(false);
      this.timerProperty = new SimpleStringProperty();
      this.model.addPropertyChangeListener(this);
   }

   public ObservableList<AirPlaneViewModel> getPlanes()
   {
      return planes;
   }

   public ObservableList<AirNodeViewModel> getAirNodes()
   {
      return airNodes;
   }

   public ObjectProperty<AirPlaneViewModel> getSelectedPlane()
   {
      return selectedPlane;
   }

   public ObjectProperty<AirNodeViewModel> getSelectedAirNode()
   {
      return selectedNode;
   }

   public BooleanProperty getSimulationFailed()
   {
      return simulationFailed;
   }

   public BooleanProperty getWindProperty()
   {
      return windProperty;
   }

   public StringProperty getTimerProperty()
   {
      return timerProperty;
   }

   public void setSelectedPlane(AirPlaneViewModel plane)
   {
      this.selectedPlane.set(plane);
   }

   public void setSelectedNode(AirNodeViewModel node)
   {
      this.selectedNode.set(node);
   }
   
   public void reRoutePlane(String callSign,StaticPosition position)
   {
      
   }

   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {

      if (evt.getPropertyName().equals("nodeADD"))
      {
         @SuppressWarnings("unchecked")
         ArrayList<NodeDTO> nodes = (ArrayList<NodeDTO>) evt.getNewValue();
         for (int i = 0; i < nodes.size(); i++)
         {
            this.airNodes.add(new AirNodeViewModel(
                  this.airNodeViewModel, nodes.get(i)));
         }
      }
      Platform.runLater(() -> {
         if (evt.getPropertyName().equals("planeADD"))
         {
            planes.add(new AirPlaneViewModel(this.planeViewModel,
                  (PlaneDTO) evt.getNewValue()));
         }
         else if (evt.getPropertyName().equals("windADD"))
         {
            windProperty.set((boolean) evt.getNewValue());
         }
         else if (evt.getPropertyName().equals("timerUPDATE"))
         {
            timerProperty.set(((Timer) evt.getNewValue()).toString());
         }
         else if (evt.getPropertyName().equals("planeREMOVE"))
         {
            planes.remove((int) evt.getNewValue());
         }
         else if (evt.getPropertyName().equals("simulationFAILED"))
         {
            simulationFailed.set((boolean) evt.getNewValue());
         }
         else if (evt.getPropertyName().equals("positionUPDATE"))
         {
            @SuppressWarnings("unchecked")
            ArrayList<PlaneDTO> planes = (ArrayList<PlaneDTO>) evt
                  .getNewValue();
            if (!this.planes.isEmpty())
            {
               for (int i = 0; i < this.planes.size(); i++)
               {
                  this.planes.get(i).getXProperty()
                        .setValue(planes.get(i).getPosition().getXCoordinate());
                  this.planes.get(i).getYProperty()
                        .setValue(planes.get(i).getPosition().getYCoordinate());
                  if (planes.get(i).getPlaneState() instanceof EmergencyState)
                  {
                     this.planes.get(i).getStatusProperty().setValue(planes
                           .get(i).getPlaneState().toString() + " - "
                           + ((EmergencyState) planes.get(i).getPlaneState())
                                 .getTime().toString());
                  }
                  else
                  {
                     this.planes.get(i).getStatusProperty()
                           .setValue(planes.get(i).getPlaneState().toString());
                  }
                  this.planes.get(i).getTargetProperty()
                        .setValue(planes.get(i).getTarget());
               }
            }
         }
      });

   }

}
