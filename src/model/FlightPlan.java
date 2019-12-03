package model;

import java.io.Serializable;
import java.util.Date;

public class FlightPlan implements Serializable
{
   private int Id;
   private Date DepartureTime;
   private Date ArrivalTime;
   private Date Delay;
   private String StartLocation;
   private String EndLocation;

   public FlightPlan(int id, Date departureTime, Date arrivalTime, Date delay,
         String startLocation, String endLocation)
   {
      this.Id = id;
      this.DepartureTime = departureTime;
      this.ArrivalTime = arrivalTime;
      this.Delay = delay;
      this.StartLocation = startLocation;
      this.EndLocation = endLocation;
   }
   
   /*public Date getArrivalTime()
   {
	   return ArrivalTime;
   }
   
   public Date getDelay()
   {
	   return Delay;
   }*/

}
