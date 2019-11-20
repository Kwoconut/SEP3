package view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

      this.viewModel.getPlanes()
            .addListener((ListChangeListener<PlaneViewModel>) change -> {
               while (change.next())
               {
                  if (change.wasAdded())
                  {
                     Pane pane = new Pane();
                     Text callSignText = new Text();
                     callSignText.textProperty().bind(change.getAddedSubList()
                           .get(0).getCallSignProperty());
                     callSignText.setFill(Color.GREEN);
                     callSignText.translateYProperty().setValue(-12);
                     callSignText.translateXProperty().setValue(10);
                     Rectangle square = new Rectangle();
                     square.scaleXProperty().setValue(20);
                     square.scaleYProperty().setValue(20);
                     square.setStroke(Color.GREEN);
                     pane.getChildren().addAll(square, callSignText);
                     pane.translateXProperty().bind(
                           change.getAddedSubList().get(0).getXProperty());
                     pane.translateYProperty().bind(
                           change.getAddedSubList().get(0).getYProperty());
                     mainPane.getChildren().add(pane);
                  }
                  else if (change.wasRemoved())
                  {
                     for (Node node : mainPane.getChildren())
                     {
                        if (node instanceof Pane)
                        {
                           if (((Pane) node).getChildren()
                                 .get(1) instanceof Text)
                           {
                              if (((Text) ((Pane) node).getChildren().get(1))
                                    .textProperty().get()
                                    .equals(change.getRemoved().get(0)
                                          .getCallSignProperty().get()))
                              {
                                 mainPane.getChildren().remove(node);
                                 return;
                              }
                           }
                        }
                     }

                  }
               }
            });

/*
 * strings.addListener((ListChangeListener<String>) change -> { while
 * (change.next()) { if (change.wasAdded()) {
 * System.out.println(change.getAddedSubList().get(0) +
 * " was added to the list!"); } else if (change.wasRemoved()) {
 * System.out.println(change.getRemoved().get(0) +
 * " was removed from the list!"); } } });
 */

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
