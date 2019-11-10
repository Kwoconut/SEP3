package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GroundNodeView
{
   private Circle circle;
   private int CircleID;

   public GroundNodeView()
   {
      this.circle = new Circle(10);
      this.circle.setFill(Color.YELLOW);
      this.circle.setStroke(Color.BLACK);
   }

   public Circle getCircle()
   {
      return circle;
   }

   public int getCircleID()
   {
      return CircleID;
   }

   public void setCircleID(int id)
   {
      CircleID = id;
   }

}
