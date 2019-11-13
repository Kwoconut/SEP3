package model;

public class TaxiState implements PlaneState
{

   @Override
   public void setNextState(Plane plane)
   {
      if (plane.isReadyForTakeOff())
      {
         plane.setState(new TakeoffState());
         plane.setSpeed(4);
      }
      else
      {
         plane.setState(new BoardingState());
         plane.setSpeed(0);
      }
   }

   @Override
   public String toString()
   {
      return "Taxi";
   }

}
