package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Position implements Serializable
{
   private double xCoordinate;
   private double yCoordinate;
   
   public Position(double xCoordinate, double yCoordinate)
   {
      this.xCoordinate = xCoordinate;
      this.yCoordinate = yCoordinate;
   }

   public double getXCoordinate()
   {
      return xCoordinate;
   }

   public double getYCoordinate()
   {
      return yCoordinate;
   }


}
