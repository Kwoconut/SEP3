package model;

public class LandingState implements PlaneState
{

   @Override
   public void setNextState(Plane plane)
   {
      plane.setState(new LandedState());
      plane.setSpeed(0);
      plane.setReadyForTakeOff(false);
   }

   @Override
   public String toString()
   {
      return "Landing";
   }

}
