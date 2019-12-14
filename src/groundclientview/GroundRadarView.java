package groundclientview;

import java.util.NoSuchElementException;

import groundclientviewmodel.GroundRadarViewModel;
import groundclientviewmodel.GroundPlaneViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GroundRadarView
{
   @FXML
   private Pane mainPane;

   @FXML
   private Label timerLabel;

   @FXML
   private ImageView flagImage;

   @FXML
   private TableView<GroundPlaneViewModel> planeListTable;

   @FXML
   private TableColumn<GroundPlaneViewModel, String> callSignColumn;

   @FXML
   private TableColumn<GroundPlaneViewModel, String> statusColumn;

   @FXML
   private TableColumn<GroundPlaneViewModel, String> targetColumn;

   @FXML
   private Pane failPane;

   private GroundRadarViewModel viewModel;

   private ObservableList<Circle> groundNodes;

   private Pane selectedPlane;

   private MainView mainView;

   public void init(GroundRadarViewModel groundRadarViewModel,
         MainView mainView)
   {
      this.mainView = mainView;
      this.viewModel = groundRadarViewModel;
      this.groundNodes = FXCollections.observableArrayList();
      this.failPane.setVisible(false);
      this.timerLabel.textProperty().bind(this.viewModel.getTimerProperty());
      for (int i = 0; i < this.viewModel.getGroundNodes().size() - 2; i++)
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
            cellData -> cellData.getValue().getRegistrationNoProperty());
      statusColumn.setCellValueFactory(
            cellData -> cellData.getValue().getStatusProperty());
      targetColumn.setCellValueFactory(
            cellData -> cellData.getValue().getTargetProperty());
      planeListTable.setItems(this.viewModel.getPlanes());

      // LISTENER CHECKING IF SIMULATION FAILED
      // FAIL PANE BECOMES VISIBLE IF TRUE

      this.viewModel.getSimulationFailed()
            .addListener((observable, oldValue, newValue) -> {
               if (newValue == true)
               {
                  failPane.setVisible(true);
               }
            });

      flagImage.setImage(new Image("Right.png"));

      this.viewModel.getWindProperty()
            .addListener((observable, oldValue, newValue) -> {
               if (newValue == true)
               {
                  flagImage.setImage(new Image("Left.png"));
               }
               else
               {
                  flagImage.setImage(new Image("Right.png"));
               }
            });

      mainPane.addEventFilter(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>()
            {
               public void handle(MouseEvent e)
               {
                  if (viewModel.getSelectedPlane().get() != null)
                  {
                     try
                     {
                        Circle circle = (Circle) mainPane.getChildren().stream()
                              .filter(node -> node instanceof Circle
                                    && node.getBoundsInParent().contains(
                                          e.getSceneX(), e.getSceneY()))
                              .findFirst().get();
                        viewModel.setSelectedGroundEndNode(viewModel
                              .getGroundNodes().stream()
                              .filter(groundNode -> groundNode.getXProperty()
                                    .get() == circle.getCenterX()
                                    && groundNode.getYProperty().get() == circle
                                          .getCenterY())
                              .findFirst().get());
                        viewModel.changePlaneRoute();
                     }
                     catch (NoSuchElementException error)
                     {
                        Circle circle = null;
                     }
                     finally
                     {
                        mainPane.getChildren().stream()
                              .filter(node -> node instanceof Pane
                                    && node.equals(selectedPlane))
                              .findFirst().get().setEffect(null);
                        viewModel.setSelectedPlane(null);
                        viewModel.setSelectedGroundStartNode(null);
                        viewModel.setSelectedGroundEndNode(null);
                     }

                  }
                  viewModel.setSelectedPlane(null);
                  viewModel.setSelectedGroundStartNode(null);
                  viewModel.setSelectedGroundEndNode(null);

               }
            });

      // LISTENER FOR ADDING OR REMOVING A PLANE FROM THE MAP
      // WHEN AN PLANE IS ADDED A NEW PANE IS CREATED WITH THE TEXT HAVING THE
      // CALLSIGN BOUND AND THE LOCATION BOUND

      this.viewModel.getPlanes()
            .addListener((ListChangeListener<GroundPlaneViewModel>) change ->

            {
               while (change.next())
               {
                  if (change.wasAdded())
                  {
                     Pane pane = new Pane();
                     Text callSignText = new Text();
                     callSignText.textProperty().bind(change.getAddedSubList()
                           .get(0).getRegistrationNoProperty());
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
                     pane.addEventFilter(MouseEvent.MOUSE_PRESSED,
                           new EventHandler<MouseEvent>()
                           {
                              public void handle(MouseEvent e)
                              {
                                 if (!change.getAddedSubList().get(0)
                                       .getStatusProperty().get()
                                       .equals("Landing")
                                       && !change.getAddedSubList().get(0)
                                             .getStatusProperty().get()
                                             .startsWith("Boarding"))
                                 {
                                    selectedPlane = pane;
                                    int depth = 70; // Setting the uniform
                                                    // variable
                                                    // for the glow width and
                                                    // height

                                    DropShadow borderGlow = new DropShadow();
                                    borderGlow.setOffsetY(0f);
                                    borderGlow.setOffsetX(0f);
                                    borderGlow.setColor(Color.RED);
                                    borderGlow.setWidth(depth);
                                    borderGlow.setHeight(depth);

                                    pane.setEffect(borderGlow); // Apply the
                                                                // borderGlow
                                                                // effect to the
                                                                // JavaFX node

                                    Circle circle = findNearestGroundNode(
                                          mainPane.getChildren(),
                                          pane.getTranslateX(),
                                          pane.getTranslateY());
                                    viewModel.setSelectedGroundStartNode(
                                          viewModel.getGroundNodes().stream()
                                                .filter(node -> node
                                                      .getXProperty()
                                                      .get() == circle
                                                            .centerXProperty()
                                                            .get()
                                                      && node.getYProperty()
                                                            .get() == circle
                                                                  .centerYProperty()
                                                                  .get())
                                                .findFirst().get());
                                    viewModel.setSelectedPlane(viewModel
                                          .getPlanes().stream()
                                          .filter(plane -> plane
                                                .getRegistrationNoProperty()
                                                .get()
                                                .equals(callSignText.getText()))
                                          .findFirst().get());
                                 }
                              }
                           });

                     mainPane.getChildren().add(pane);
                  }
                  else if (change.wasRemoved())
                  {
                     for (Node node : mainPane.getChildren())
                     {
                        if (node instanceof Pane && !node.equals(failPane))
                        {
                           if (((Pane) node).getChildren()
                                 .get(1) instanceof Text)
                           {
                              if (((Text) ((Pane) node).getChildren().get(1))
                                    .textProperty().get()
                                    .equals(change.getRemoved().get(0)
                                          .getRegistrationNoProperty().get()))
                              {
                                 node.setVisible(false);
                              }
                           }
                        }
                     }

                  }
               }
            });

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
