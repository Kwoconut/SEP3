package model;

public class LandedState implements PlaneState
{

   @Override
   public void setNextState(Plane plane)
   {
      plane.setState(new TaxiState());
      plane.setSpeed(2);
      

   }

   @Override
   public String toString()
   {
      return "Landed";
   }

}
