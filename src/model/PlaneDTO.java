package model;

import java.io.Serializable;

public class PlaneDTO implements Serializable
{
   private String callSign;
   private Position position;
   private PlaneState state;

   public PlaneDTO(String callSign, PlaneState state, Position position)
   {
      this.callSign = callSign;
      this.state = state;
      this.position = position;
   }

   public String getCallSign()
   {
      return callSign;
   }

   public PlaneState getPlaneState()
   {
      return state;
   }
   
   public Position getPosition()
   {
      return position;
   }


}
