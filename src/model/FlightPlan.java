package model;

import java.io.Serializable;

public class FlightPlan implements Serializable
{
   private int Id;
   private FlightDate DepartureTime;
   private FlightDate ArrivalTime;
   private Timer Delay;
   private String StartLocation;
   private String EndLocation;

   public FlightPlan(int id, FlightDate departureTime, FlightDate arrivalTime,
         Timer delay, String startLocation, String endLocation)
   {
      this.Id = id;
      this.DepartureTime = departureTime;
      this.ArrivalTime = arrivalTime;
      this.Delay = delay;
      this.StartLocation = startLocation;
      this.EndLocation = endLocation;
   }

   public String getStartLocation()
   {
      return StartLocation;
   }

   public String getEndLocation()
   {
      return EndLocation;
   }

   public Timer getDelay()
   {
      return Delay;
   }

   public FlightDate getDepartureTime()
   {
      return DepartureTime;
   }

   public FlightDate getArrivalTime()
   {
      return ArrivalTime;
   }

   public int getID()
   {
      return Id;
   }

   public String toString()
   {
      return Id + " " + StartLocation + " " + EndLocation + " " + DepartureTime
            + " " + ArrivalTime + " " + Delay;
   }

}
