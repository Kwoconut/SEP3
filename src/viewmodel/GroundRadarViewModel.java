package viewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.GroundNodeModel;
import model.GroundRadarModel;
import model.Plane;
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

   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {
      Platform.runLater(() -> {
         if (evt.getPropertyName().equals("planeADD"))
         {
            planes.add(new PlaneViewModel(this.planeViewModel,
                  (Plane) evt.getNewValue()));
         }
      });

   }

}
