package viewmodel;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.GroundNode;
import model.GroundRadarModel;
import model.Plane;

public class GroundRadarViewModel
{
   private GroundRadarModel model;
   private ObservableList<PlaneViewModel> planes;
   private ObjectProperty<PlaneViewModel> selectedPlane;
   private ObservableList<GroundNodeViewModel> groundNodes;
   private ObjectProperty<GroundNodeViewModel> selectedNode;

   public GroundRadarViewModel(GroundRadarModel model)
   {
      this.model = model;
      this.planes = FXCollections.observableArrayList();
      this.groundNodes = FXCollections.observableArrayList();
   }

   public ObservableList<GroundNodeViewModel> getGroundNodes()
   {
      ArrayList<GroundNode> modelNodes = model.getGroundNodes();

      for (int i = 0; i < modelNodes.size(); i++)
      {
         groundNodes.add(new GroundNodeViewModel(
               modelNodes.get(i).getPosition().getXCoordinate(),
               modelNodes.get(i).getPosition().getYCoordinate(),
               modelNodes.get(i).getNodeId(), modelNodes.get(i).getName()));
      }

      return groundNodes;

   }

   public ObservableList<PlaneViewModel> getPlanes()
   {
      ArrayList<Plane> modelPlanes = model.getPlanes();

      planes.add(new PlaneViewModel(modelPlanes.get(0).getCallSign(),
            modelPlanes.get(0).getStatus(),
            modelPlanes.get(0).getPosition().getXCoordinate(),
            modelPlanes.get(0).getPosition().getYCoordinate()));
      for (int i = 0; i < modelPlanes.size(); i++)
      {
         if (!planes.get(i).getCallSignProperty().get()
               .equals(modelPlanes.get(i).getCallSign()) || planes.size() == 0)
         {
            planes.add(new PlaneViewModel(modelPlanes.get(i).getCallSign(),
                  modelPlanes.get(i).getStatus(),
                  modelPlanes.get(i).getPosition().getXCoordinate(),
                  modelPlanes.get(i).getPosition().getYCoordinate()));
         }

      }
      return planes;
   }

   public void setSelectedPlane(PlaneViewModel selectedPlane)
   {
      this.selectedPlane = new SimpleObjectProperty<PlaneViewModel>(
            selectedPlane);
   }

   public void setSelectedNode(GroundNodeViewModel selectedNode)
   {
      this.selectedNode = new SimpleObjectProperty<GroundNodeViewModel>(
            selectedNode);
   }

   public int[] movePlane(int startLocation, int endLocation)
   {
      return this.model.movePlane(startLocation, endLocation);
   }

}
