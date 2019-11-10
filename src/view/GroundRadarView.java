package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import viewmodel.GroundNodeViewModel;
import viewmodel.GroundRadarViewModel;
import viewmodel.PlaneViewModel;

public class GroundRadarView
{

   private GroundRadarViewModel viewModel;

   @FXML
   private Pane mainPane;

   private ObservableList<PlaneViewModel> planes;
   private ObservableList<Pane> planeObjects;
   private PlaneViewModel lastPlane;
   private Pane selectedPane;
   private Circle selectedCircle;
   private ObservableList<GroundNodeView> groundNodes;

   public void init(GroundRadarViewModel viewModel)
   {
      this.viewModel = viewModel;
      lastPlane = null;
      planeObjects = FXCollections.observableArrayList();
      this.groundNodes = FXCollections.observableArrayList();
      ObservableList<GroundNodeViewModel> groundNodesViewModel = this.viewModel
            .getGroundNodes();

      for (int i = 0; i < groundNodesViewModel.size(); i++)
      {
         this.groundNodes.add(new GroundNodeView());
         this.groundNodes.get(i).getCircle().centerXProperty()
               .bind(groundNodesViewModel.get(i).getXPositionProperty());
         this.groundNodes.get(i).getCircle().centerYProperty()
               .bind(groundNodesViewModel.get(i).getYPositionProperty());
         this.groundNodes.get(i)
               .setCircleID(groundNodesViewModel.get(i).getIdProperty().get());
         this.mainPane.getChildren().add(this.groundNodes.get(i).getCircle());
      }

      mainPane.addEventFilter(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>()
            {
               @Override
               public void handle(MouseEvent e)
               {
                  if (selectedPane != null)
                  {
                     int startLocation = 0;
                     int endLocation = 0;
                     System.out.println(selectedCircle);
                     for (int i = 0; i < groundNodes.size(); i++)
                     {
                        if (groundNodes.get(i).getCircle()
                              .equals((selectedCircle)))
                        {
                           startLocation = groundNodes.get(i).getCircleID();
                        }

                        if (groundNodes.get(i).getCircle().getBoundsInParent()
                              .contains(e.getSceneX(), e.getSceneY()))
                        {
                           endLocation = groundNodes.get(i).getCircleID();
                        }
                     }

                     int shortestPathId[] = viewModel.movePlane(startLocation,
                           endLocation);

                     Polyline path = new Polyline();
/*
 * path.getPoints().add(selectedPane.getTranslateX() + 10);
 * path.getPoints().add(selectedPane.getTranslateY() + 10);
 */
                     for (int i = 0; i < shortestPathId.length; i++)
                     {

                        path.getPoints().add(groundNodes.get(shortestPathId[i])
                              .getCircle().centerXProperty().get());
                        path.getPoints().add(groundNodes.get(shortestPathId[i])
                              .getCircle().centerYProperty().get());
                     }

                     System.out.println(path.getPoints());

                     PathTransition pathTransition = new PathTransition();
                     pathTransition.setNode(selectedPane);
                     pathTransition.setPath(path);
                     pathTransition.setDuration(
                           Duration.seconds(5 * shortestPathId.length));
                     pathTransition.play();
                     selectedPane.setEffect(null);
                     selectedPane = null;
                     selectedCircle = null;

                  }
               }
            });

      AnimationTimer timer = new AnimationTimer()
      {
         @Override
         public void handle(long now)
         {
            onUpdate();
         }
      };
      timer.start();

   }

   private void onUpdate()
   {
      planes = viewModel.getPlanes();
      if (!(planes.get(planes.size() - 1).equals(lastPlane)))
      {
         lastPlane = planes.get(planes.size() - 1);
         Pane pane = new Pane();
         Text text = new Text();
         planeObjects.add(pane);
         mainPane.getChildren().add(pane);

         text.textProperty()
               .bind(planes.get(planes.size() - 1).getCallSignProperty());
         text.setStroke(Color.GREEN);

         pane.setPrefSize(20, 20);

         pane.getChildren().add(text);
         text.xProperty().set(20);
         text.setFill(Color.GREEN);

         pane.setStyle("-fx-background-color: green");

         PathTransition transition = new PathTransition();

         transition.setNode(pane);
         transition.setDuration(Duration.seconds(20));
         transition.setPath(new Polyline(80, 114, 1300, 114));
         transition.play();

         pane.addEventFilter(MouseEvent.MOUSE_PRESSED,
               new EventHandler<MouseEvent>()
               {
                  @Override
                  public void handle(MouseEvent e)
                  {
                     if (selectedCircle == null)
                     {
                        selectedPane = pane;
                        int depth = 70; // Setting the uniform variable for the
                                        // glow width and height

                        DropShadow borderGlow = new DropShadow();
                        borderGlow.setOffsetY(0f);
                        borderGlow.setOffsetX(0f);
                        borderGlow.setColor(Color.RED);
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);

                        pane.setEffect(borderGlow);
                        selectedCircle = findNearestGroundNode(
                              mainPane.getChildren(),
                              pane.translateXProperty().get(),
                              pane.translateYProperty().get());
                        System.out.println(selectedCircle);
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