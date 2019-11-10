package model;

import java.io.Serializable;

public class Position implements Serializable
{
   private int xCoordinate;
   private int yCoordinate;

   public Position(int xCoordinate, int yCoordinate)
   {
      this.xCoordinate = xCoordinate;
      this.yCoordinate = yCoordinate;
   }

   public int getXCoordinate()
   {
      return xCoordinate;
   }

   public int getYCoordinate()
   {
      return yCoordinate;
   }

}
