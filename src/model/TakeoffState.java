package model;

public class TakeoffState implements PlaneState
{

   @Override
   public void setNextState(Plane plane)
   {
      plane.setState(new InAirState());
      plane.setSpeed(10);

   }

   @Override
   public String toString()
   {
      return "Takeoff";
   }

}
