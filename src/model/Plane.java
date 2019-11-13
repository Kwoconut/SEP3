package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javafx.application.Platform;

public class Plane implements Serializable
{
   private String CallSign;
   private String Model;
   private String Company;
   private String Status;
//   private FlightPlan FlightPlan;
   private Position Position;
   
   public Plane(String callSign, String model, String company, String status,
          Position position)
   {
      this.CallSign = callSign;
      this.Model = model;
      this.Company = company;
      this.Status = status;
  //    this.FlightPlan = flightPlan;
      this.Position = position;
   }

   public String getCallSign()
   {
      return CallSign;
   }

   public String getModel()
   {
      return Model;
   }

   public String getCompany()
   {
      return Company;
   }

   public String getStatus()
   {
      return Status;
   }

/*   public FlightPlan getFlightPlan()
   {
      return FlightPlan;
   }*/

   public Position getPosition()
   {
      return Position;
   }

}
