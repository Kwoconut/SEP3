package viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.GroundNodeModel;

public class GroundNodeViewModel
{

   private GroundNodeModel model;
   private IntegerProperty xPositionProperty;
   private IntegerProperty yPositionProperty;
   private IntegerProperty idProperty;
   private StringProperty nameProperty;

   public GroundNodeViewModel(GroundNodeModel model)
   {
      this.model = model;
      this.xPositionProperty = new SimpleIntegerProperty(0);
      this.yPositionProperty = new SimpleIntegerProperty(0);
      this.idProperty = new SimpleIntegerProperty(0);
      this.nameProperty = new SimpleStringProperty("");
   }

   public GroundNodeViewModel(int xPosition, int yPosition, int id, String name)
   {
      this.xPositionProperty = new SimpleIntegerProperty(xPosition);
      this.yPositionProperty = new SimpleIntegerProperty(yPosition);
      this.idProperty = new SimpleIntegerProperty(id);
      this.nameProperty = new SimpleStringProperty(name);
   }

   public IntegerProperty getXPositionProperty()
   {
      return xPositionProperty;
   }

   public IntegerProperty getYPositionProperty()
   {
      return yPositionProperty;
   }

   public IntegerProperty getIdProperty()
   {
      return idProperty;
   }

   public StringProperty getNameProperty()
   {
      return nameProperty;
   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof GroundNodeViewModel))
      {
         return false;
      }
      GroundNodeViewModel other = (GroundNodeViewModel) obj;
      return other.getIdProperty().get() == idProperty.get();
   }

}
