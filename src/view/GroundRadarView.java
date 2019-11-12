package view;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

   private PlaneViewModel lastPlane;

   private ObservableList<Pane> planePanes;
   private ObservableList<Circle> groundNodes;
   private Pane selectedPlane;

   public void init(GroundRadarViewModel groundRadarViewModel)
   {
      planePanes = FXCollections.observableArrayList();
      groundNodes = FXCollections.observableArrayList();
      lastPlane = null;
      selectedPlane = null;
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

      mainPane.addEventFilter(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>()
            {
               @Override
               public void handle(MouseEvent e)
               {
                  if (selectedPlane != null)
                  {
                     for (int i = 0; i < groundNodes.size(); i++)
                     {
                        if (groundNodes.get(i).getBoundsInParent()
                              .contains(e.getSceneX(), e.getSceneY()))
                        {
                           viewModel.setSelectedGroundEndNode(
                                 viewModel.getGroundNodes().get(i));
                        }
                     }
                     double[] points = viewModel.movePlane();
                     selectedPlane.setEffect(null);
                     PathTransition pathTransition = new PathTransition();
                     Polyline polyline = new Polyline();
                     polyline.getPoints().addAll(selectedPlane.getTranslateX()+10,
                           selectedPlane.getTranslateY()+10);
                     for (int i = 0; i < points.length; i++)
                     {
                        polyline.getPoints().add(points[i]);
                     }
                     pathTransition.setNode(selectedPlane);
                     pathTransition.setPath(polyline);
                     pathTransition
                           .setDuration(Duration.seconds(points.length * 5));
                     for (int i = 0; i < viewModel.getPlanes().size(); i++)
                     {
                        if (viewModel.getPlanes().get(i).getCallSignProperty()
                              .get().equals(((Text) (selectedPlane.getChildren()
                                    .get(0))).textProperty().get()))
                        {
                           viewModel.getPlanes().get(i).getStatusProperty()
                                 .setValue("Taxi");
                        }
                     }

                     pathTransition.play();
                     selectedPlane = null;
                     viewModel.setSelectedGroundStartNode(null);
                     viewModel.setSelectedGroundEndNode(null);
                  }

               }

            });

      AnimationTimer timer = new AnimationTimer()
      {
         public void handle(long now)
         {
            updatePlanes();
         }
      };
      timer.start();

   }

   private void updatePlanes()
   {
      if (this.viewModel.getPlanes().isEmpty())
      {
         return;
      }

      if (!(this.viewModel.getPlanes()
            .get(this.viewModel.getPlanes().size() - 1).equals(lastPlane)))
      {
         lastPlane = this.viewModel.getPlanes()
               .get(this.viewModel.getPlanes().size() - 1);
         Pane pane = new Pane();
         pane.setPrefSize(20, 20);
         pane.setStyle("-fx-background-color: green");
         Text callSignText = new Text();
         callSignText.textProperty()
               .bind(this.viewModel.getPlanes()
                     .get(this.viewModel.getPlanes().size() - 1)
                     .getCallSignProperty());
         callSignText.setFill(Color.GREEN);
         callSignText.translateYProperty().setValue(-5);
         callSignText.translateXProperty().setValue(10);
         pane.translateXProperty().bindBidirectional(lastPlane.getXProperty());
         pane.translateYProperty().bindBidirectional(lastPlane.getYProperty());
         pane.getChildren().addAll(callSignText);
         mainPane.getChildren().add(pane);
         PathTransition transition = new PathTransition();
         Line line = new Line(0, 114, 1300, 114);
         transition.setNode(pane);
         transition.setPath(line);
         transition.setDuration(Duration.seconds(10));
         transition.setOnFinished(event -> {
            for (int i = 0; i < this.viewModel.getPlanes().size(); i++)
            {
               if (this.viewModel.getPlanes().get(i).getCallSignProperty().get()
                     .equals(callSignText.textProperty().get()))
               {
                  this.viewModel.getPlanes().get(i).getStatusProperty()
                        .setValue("Landed");
               }
            }
         });
         transition.play();

         pane.addEventFilter(MouseEvent.MOUSE_PRESSED,
               new EventHandler<MouseEvent>()
               {
                  @Override
                  public void handle(MouseEvent e)
                  {
                     if (viewModel.getSelectedStartNode().get() == null)
                     {
                        selectedPlane = pane;
                        int depth = 70; // Setting the uniform variable for the
                                        // glow width and height

                        DropShadow borderGlow = new DropShadow();
                        borderGlow.setOffsetY(0f);
                        borderGlow.setOffsetX(0f);
                        borderGlow.setColor(Color.RED);
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);

                        pane.setEffect(borderGlow);
                        Circle circle = findNearestGroundNode(
                              mainPane.getChildren(),
                              pane.translateXProperty().get(),
                              pane.translateYProperty().get());
                        for (int i = 0; i < groundNodes.size(); i++)
                        {
                           if (circle.equals(groundNodes.get(i)))
                           {
                              viewModel.setSelectedGroundStartNode(
                                    viewModel.getGroundNodes().get(i));
                              return;
                           }
                        }
                     }

                  }
               });

      }

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
