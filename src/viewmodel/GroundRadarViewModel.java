package viewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.GroundNodeModel;
import model.GroundRadarModel;
import model.PlaneDTO;
import model.PlaneModel;

public class GroundRadarViewModel implements PropertyChangeListener
{
   private ObservableList<PlaneViewModel> planes;
   private ObservableList<GroundNodeViewModel> groundNodes;
   private ObjectProperty<GroundNodeViewModel> selectedStartNode;
   private ObjectProperty<GroundNodeViewModel> selectedEndNode;
   private ObjectProperty<PlaneViewModel> selectedPlane;
   private GroundRadarModel model;
   private GroundNodeModel groundNodeViewModel;
   private PlaneModel planeViewModel;
   private BooleanProperty simulationFailed;

   public GroundRadarViewModel(GroundRadarModel model,
         GroundNodeModel groundNodeViewModel, PlaneModel planeViewModel)
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
      for (int i = 0; i < this.model.getGroundNodes().size(); i++)
      {
         this.groundNodes.add(new GroundNodeViewModel(this.groundNodeViewModel,
               this.model.getGroundNodes().get(i)));
      }
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
   
   public BooleanProperty getSimulationFailed()
   {
      return simulationFailed;
   }

   public void setSelectedGroundStartNode(GroundNodeViewModel selectedNode)
   {
      this.selectedStartNode.setValue(selectedNode);

   }

   public void setSelectedGroundEndNode(GroundNodeViewModel selectedNode)
   {
      this.selectedEndNode.setValue(selectedNode);
   }

   public void setSelectedPlane(PlaneViewModel plane)
   {
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
      Platform.runLater(() -> {
         if (evt.getPropertyName().equals("planeADD"))
         {
            planes.add(new PlaneViewModel(this.planeViewModel,
                  (PlaneDTO) evt.getNewValue()));
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
            ArrayList<PlaneDTO> planes = (ArrayList<PlaneDTO>) evt.getNewValue();
            if (!this.planes.isEmpty())
            {
            for (int i = 0 ; i < this.planes.size();i++)
            {
               this.planes.get(i).getXProperty().setValue(planes.get(i).getPosition().getXCoordinate());
               this.planes.get(i).getYProperty().setValue(planes.get(i).getPosition().getYCoordinate());
            }
            }
         }
      });

   }

}
