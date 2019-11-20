package model;

import java.io.Serializable;

public class PlaneDTO implements Serializable
{
   private String callSign;
   private StaticPosition staticPosition;
   private PlaneState state;

   public PlaneDTO(String callSign, PlaneState state, StaticPosition staticPosition)
   {
      this.callSign = callSign;
      this.state = state;
      this.staticPosition = staticPosition;
   }

   public String getCallSign()
   {
      return callSign;
   }

   public PlaneState getPlaneState()
   {
      return state;
   }
   
   public StaticPosition getPosition()
   {
      return staticPosition;
   }


}
