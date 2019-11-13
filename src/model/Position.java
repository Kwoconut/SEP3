package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Position implements Serializable
{
   private double XCoordinate;
   private double YCoordinate;
   
   public Position(double xCoordinate, double yCoordinate)
   {
      this.XCoordinate = xCoordinate;
      this.YCoordinate = yCoordinate;
   }

   public double getXCoordinate()
   {
      return XCoordinate;
   }

   public double getYCoordinate()
   {
      return YCoordinate;
   }


}
