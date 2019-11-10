package viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GroundRadarModel;
import model.PlaneModel;
import model.Position;

public class PlaneViewModel
{
   private StringProperty callSignProperty;
   private StringProperty statusProperty;
   private IntegerProperty xProperty;
   private IntegerProperty yProperty;

   public PlaneViewModel()
   {
      callSignProperty = new SimpleStringProperty("");
      statusProperty = new SimpleStringProperty("");
      xProperty = new SimpleIntegerProperty(0);
      yProperty = new SimpleIntegerProperty(0);
   }

   public PlaneViewModel(String callSignProperty, String statusProperty,
         int xProperty, int yProperty)
   {
      this.callSignProperty = new SimpleStringProperty(callSignProperty);
      this.statusProperty = new SimpleStringProperty(statusProperty);
      this.xProperty = new SimpleIntegerProperty(xProperty);
      this.yProperty = new SimpleIntegerProperty(yProperty);
   }

   public StringProperty getCallSignProperty()
   {
      return callSignProperty;
   }

   public StringProperty getStatusProperty()
   {
      return statusProperty;
   }

   public IntegerProperty getXProperty()
   {
      return xProperty;
   }

   public IntegerProperty getYProperty()
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
