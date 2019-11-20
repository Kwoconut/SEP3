package model;

import java.io.Serializable;

public class Position implements Serializable
{
   private double XCoordinate;
   private double YCoordinate;

   public Position(double XCoordinate, double YCoordinate)
   {
      this.XCoordinate = XCoordinate;
      this.YCoordinate = YCoordinate;
   }

   public double getXCoordinate()
   {
      return XCoordinate;
   }

   public double getYCoordinate()
   {
      return YCoordinate;
   }

   public boolean movePosition(Position position, double speed)
   {
      double distance = Math.sqrt((position.getXCoordinate() - XCoordinate)
            * (position.getXCoordinate() - XCoordinate)
            + (position.getYCoordinate() - YCoordinate)
                  * (position.getYCoordinate() - YCoordinate));

      if (distance > 10)
      {
         double deltaX = position.getXCoordinate() - XCoordinate;
         double deltaY = position.getYCoordinate() - YCoordinate;
         double angle = Math.atan2(deltaY, deltaX);
         XCoordinate += speed * Math.cos(angle);
         YCoordinate += speed * Math.sin(angle);
         return false;
      }
      else
      {
         XCoordinate = position.getXCoordinate();
         YCoordinate = position.getYCoordinate();
         return true;
      }
   }

   public void setPosition(Position position)
   {
      this.XCoordinate = position.getXCoordinate();
      this.YCoordinate = position.getYCoordinate();
   }

}
