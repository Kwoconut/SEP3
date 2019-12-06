package groundclientviewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import groundClientModel.GroundRadarModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.NodeDTO;
import model.NodeModel;
import model.PlaneDTO;
import model.PlaneModel;
import model.Timer;

public class GroundRadarViewModel implements PropertyChangeListener
{
   private ObservableList<PlaneViewModel> planes;
   private ObservableList<GroundNodeViewModel> groundNodes;
   private ObjectProperty<GroundNodeViewModel> selectedStartNode;
   private ObjectProperty<GroundNodeViewModel> selectedEndNode;
   private ObjectProperty<PlaneViewModel> selectedPlane;
   private GroundRadarModel model;
   private NodeModel groundNodeViewModel;
   private PlaneModel planeViewModel;
   private BooleanProperty simulationFailed;
   private BooleanProperty windProperty;
   private StringProperty timerProperty;

   public GroundRadarViewModel(GroundRadarModel model,
         NodeModel groundNodeViewModel, PlaneModel planeViewModel)
   {
      this.model = model;
      this.groundNodeViewModel = groundNodeViewModel;
      this.planeViewModel = planeViewModel;
      this.planes = FXCollections.observableArrayList();
      this.groundNodes = FXCollections.observableArrayList();
      this.selectedStartNode = new SimpleObjectProperty<GroundNodeViewModel>();
      this.selectedEndNode = new SimpleObjectProperty<GroundNodeViewModel>();
      this.selectedPlane = new SimpleObjectProperty<PlaneViewModel>();
      this.simulationFailed = new SimpleBooleanProperty(false);
      this.windProperty = new SimpleBooleanProperty(false);
      this.timerProperty = new SimpleStringProperty();
      this.model.addPropertyChangeListener(this);

   }

   public ObservableList<PlaneViewModel> getPlanes()
   {
      return planes;
   }

   public ObservableList<GroundNodeViewModel> getGroundNodes()
   {
      return groundNodes;
   }

   public ObjectProperty<GroundNodeViewModel> getSelectedStartNode()
   {
      return selectedStartNode;
   }

   public ObjectProperty<GroundNodeViewModel> getSelectedEndNode()
   {
      return selectedEndNode;
   }

   public ObjectProperty<PlaneViewModel> getSelectedPlane()
   {

      return selectedPlane;

   }

   public BooleanProperty getWindProperty()
   {
      return windProperty;
   }

   public BooleanProperty getSimulationFailed()
   {
      return simulationFailed;
   }
   
   public StringProperty getTimerProperty()
   {
      return timerProperty;
   }

   public void setSelectedGroundStartNode(GroundNodeViewModel selectedNode)
   {
      if (selectedNode != null)
      {
         System.out.println(selectedNode.getIDProperty().get() + " ");
      }
      this.selectedStartNode.setValue(selectedNode);

   }

   public void setSelectedGroundEndNode(GroundNodeViewModel selectedNode)
   {
      if (selectedNode != null)
      {
         System.out.println(selectedNode.getIDProperty().get() + " ");
      }
      this.selectedEndNode.setValue(selectedNode);
   }

   public void setSelectedPlane(PlaneViewModel plane)
   {
      if (plane != null)
      {
         System.out.println(plane.getCallSignProperty().get() + " ");
      }
      this.selectedPlane.setValue(plane);
   }

   public void changePlaneRoute()
   {
      this.model.changePlaneRoute(
            selectedPlane.get().getCallSignProperty().get(),
            selectedStartNode.get().getIDProperty().get(),
            selectedEndNode.get().getIDProperty().get());
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
            this.groundNodes.add(new GroundNodeViewModel(
                  this.groundNodeViewModel, nodes.get(i)));
         }
      }
      Platform.runLater(() -> {
         if (evt.getPropertyName().equals("planeADD"))
         {
            planes.add(new PlaneViewModel(this.planeViewModel,
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
                  this.planes.get(i).getStatusProperty()
                        .setValue(planes.get(i).getPlaneState().toString());
               }
            }
         }
      });

   }


}
