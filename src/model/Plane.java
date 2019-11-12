package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javafx.application.Platform;

public class Plane implements Serializable
{
   private String callSign;
   private String model;
   private String company;
   private String status;
   private FlightPlan flightPlan;
   private Position position;
   private double speed;
   public Plane(String callSign, String model, String company, String status,
         FlightPlan flightPlan, Position position, double speed)
   {
      this.callSign = callSign;
      this.model = model;
      this.company = company;
      this.status = status;
      this.flightPlan = flightPlan;
      this.position = position;
      this.speed = speed;
   }

   public String getCallSign()
   {
      return callSign;
   }

   public String getModel()
   {
      return model;
   }

   public String getCompany()
   {
      return company;
   }

   public String getStatus()
   {
      return status;
   }

   public FlightPlan getFlightPlan()
   {
      return flightPlan;
   }

   public Position getPosition()
   {
      return position;
   }

   public double getSpeed()
   {
      return speed;
   }

}
