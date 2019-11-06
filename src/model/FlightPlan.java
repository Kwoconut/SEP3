package model;

import java.io.Serializable;
import java.util.Date;

public class FlightPlan implements Serializable
{
   private String id;
   private Date departureTime;
   private Date arrivalTime;
   private Date delay;
   private String startLocation;
   private String endLocation;

   public FlightPlan(String id, Date departureTime, Date arrivalTime,
         Date delay, String startLocation, String endLocation)
   {
      this.id = id;
      this.departureTime = departureTime;
      this.arrivalTime = arrivalTime;
      this.delay = delay;
      this.startLocation = startLocation;
      this.endLocation = endLocation;
   }

}
