package model;

public class BoardingState implements PlaneState
{

   private int time;

   public BoardingState()
   {
      time = 1000;
   }

   @Override
   public void setNextState(Plane plane)
   {
      plane.setState(new TaxiState());
      plane.setSpeed(2);
      plane.setReadyForTakeOff(true);
   }

   @Override
   public String toString()
   {
      return "Boarding";
   }

   public void decrement()
   {
      time--;
   }

}
