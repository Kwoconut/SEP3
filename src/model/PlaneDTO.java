package model;

import java.io.Serializable;

public class PlaneDTO implements Serializable
{
   private String callSign;
   private StaticPosition staticPosition;
   private PlaneState state;
   private String target;

   public PlaneDTO(String callSign, PlaneState state, StaticPosition staticPosition,String target)
   {
      this.callSign = callSign;
      this.state = state;
      this.staticPosition = staticPosition;
      this.target = target;
   }
   
   public String getTarget()
   {
      return target;
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
