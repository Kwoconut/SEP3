package groundclientviewmodel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.PlaneDTO;
import model.PlaneModel;

public class PlaneViewModel
{
   private StringProperty callSignProperty;
   private StringProperty statusProperty;
   private DoubleProperty xProperty;
   private DoubleProperty yProperty;
   private PlaneModel model;

   public PlaneViewModel(PlaneModel model, PlaneDTO plane)
   {
      this.model = model;
      callSignProperty = new SimpleStringProperty(plane.getCallSign());
      statusProperty = new SimpleStringProperty(
            plane.getPlaneState().toString());
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
