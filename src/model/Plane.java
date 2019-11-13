package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Platform;

public class Plane implements Serializable
{
   private String CallSign;
   private String Model;
   private String Company;
   private String Status;
//   private FlightPlan FlightPlan;
   private Position Position;
   private Position Target;
   private ArrayList<Position> Route;
   private double Speed;

   public Plane(String callSign, String model, String company, String status,
         Position position, double Speed)
   {
      this.CallSign = callSign;
      this.Model = model;
      this.Company = company;
      this.Status = status;
      // this.FlightPlan = flightPlan;
      this.Position = position;
      this.Speed = Speed;
      this.Target = null;
      this.Route = null;
   }

   public Position getTarget()
   {
      return Target;
   }

   public synchronized void movePlane()
   {
      if (!Status.equals("LANDED"))
      {
         boolean targetReached = Position.movePosition(Target, Speed);
         if (targetReached)
         {
            Route.remove(0);
            if (Route.isEmpty())
            {
               Status = "LANDED";
            }
            else
            {
               Target.setPosition(Route.get(0));
            }
         }
      }

   }

   public void setRoute(ArrayList<Position> Route)
   {
      this.Route = Route;
      this.Target = Route.get(0);
      this.Status = "TAXI";
   }

   public void stopPlane()
   {
      this.Status = "LANDED";
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

/*
 * public FlightPlan getFlightPlan() { return FlightPlan; }
 */

   public Position getPosition()
   {
      return Position;
   }

}
