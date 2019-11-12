package viewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GroundRadarModel;
import model.Plane;
import model.PlaneModel;
import model.Position;

public class PlaneViewModel
{
   private StringProperty callSignProperty;
   private StringProperty statusProperty;
   private DoubleProperty xProperty;
   private DoubleProperty yProperty;
   private PlaneModel model;

   public PlaneViewModel(PlaneModel model, Plane plane)
   {
      this.model = model;
      callSignProperty = new SimpleStringProperty(plane.getCallSign());
      statusProperty = new SimpleStringProperty(plane.getStatus());
      xProperty = new SimpleDoubleProperty(
            plane.getPosition().getXCoordinate());
      yProperty = new SimpleDoubleProperty(
            plane.getPosition().getYCoordinate());
   }

   public StringProperty getCallSignProperty()
   {
      return callSignProperty;
   }

   public StringProperty getStatusProperty()
   {
      return statusProperty;
   }

   public DoubleProperty getXProperty()
   {
      return xProperty;
   }

   public DoubleProperty getYProperty()
   {
      return yProperty;
   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof PlaneViewModel))
      {
         return false;
      }
      PlaneViewModel other = (PlaneViewModel) obj;
      return other.getCallSignProperty().get()
            .equals(this.getCallSignProperty().get())
            && other.getStatusProperty().get()
                  .equals(this.statusProperty.get());
   }

}
