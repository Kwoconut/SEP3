package model;

public class Plane
{
   private String callSign;
   private String model;
   private String company;
   private String status;
   private FlightPlan flightPlan;
   private Position position;

   public Plane(String callSign, String model, String company, String status,
         FlightPlan flightPlan, Position position)
   {
      this.callSign = callSign;
      this.model = model;
      this.company = company;
      this.status = status;
      this.flightPlan = flightPlan;
      this.position = position;
   }

}
