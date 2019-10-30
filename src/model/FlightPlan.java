package model;

import java.util.Date;

public class FlightPlan
{
   private String id;
   private Date departureTime;
   private Date arrivalTime;
   private Date delay;
   private Route route;

   public FlightPlan(String id, Date departureTime, Date arrivalTime,
         Date delay, Route route)
   {
   this.id = id;
   this.departureTime = departureTime;
   this.arrivalTime = arrivalTime;
   this.delay = delay;
   this.route = route;
   }

}
