package model;

public class InAirState implements PlaneState
{

   @Override
   public void setNextState(Plane plane)
   {
      plane.setState(new LandingState());
      plane.setSpeed(4);
   }

   @Override
   public String toString()
   {
      return "In Air";
   }
}
