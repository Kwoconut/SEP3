package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import viewmodel.GroundRadarViewModel;
import viewmodel.PlaneViewModel;

public class GroundRadarView
{
   @FXML
   private Pane mainPane;

   @FXML
   private TableView<PlaneViewModel> planeListTable;

   @FXML
   private TableColumn<PlaneViewModel, String> callSignColumn;

   @FXML
   private TableColumn<PlaneViewModel, String> statusColumn;

   private GroundRadarViewModel viewModel;

   private ObservableList<Circle> groundNodes;

   public void init(GroundRadarViewModel groundRadarViewModel)
   {
      groundNodes = FXCollections.observableArrayList();
      this.viewModel = groundRadarViewModel;
      for (int i = 0; i < this.viewModel.getGroundNodes().size(); i++)
      {
         Circle circle = new Circle(10);
         circle.centerXProperty()
               .bind(this.viewModel.getGroundNodes().get(i).getXProperty());
         circle.centerYProperty()
               .bind(this.viewModel.getGroundNodes().get(i).getYProperty());
         circle.setFill(Color.YELLOW);
         circle.setStroke(Color.BLACK);
         groundNodes.add(circle);
         mainPane.getChildren().add(circle);
      }

      callSignColumn.setCellValueFactory(
            cellData -> cellData.getValue().getCallSignProperty());
      statusColumn.setCellValueFactory(
            cellData -> cellData.getValue().getStatusProperty());
      planeListTable.setItems(this.viewModel.getPlanes());

   }

   private Circle findNearestGroundNode(ObservableList<Node> nodes, double x,
         double y)
   {
      Point2D pClick = new Point2D(x, y);
      Circle nearestNode = null;
      double closestDistance = Double.POSITIVE_INFINITY;

      for (Node node : nodes)
      {
         if (node instanceof Circle)
         {
            Bounds bounds = node.getBoundsInParent();
            Point2D[] corners = new Point2D[] {
                  new Point2D(bounds.getMinX(), bounds.getMinY()),
                  new Point2D(bounds.getMaxX(), bounds.getMinY()),
                  new Point2D(bounds.getMaxX(), bounds.getMaxY()),
                  new Point2D(bounds.getMinX(), bounds.getMaxY()), };

            for (Point2D pCompare : corners)
            {
               double nextDist = pClick.distance(pCompare);
               if (nextDist < closestDistance)
               {
                  closestDistance = nextDist;
                  nearestNode = (Circle) node;
               }
            }
         }
      }

      return nearestNode;
   }

}
