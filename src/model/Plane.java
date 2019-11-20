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
//   private FlightPlan FlightPlan;
   private PlaneState PlaneState;
   private Position Position;
   private Position Target;
   private ArrayList<Position> Route;
   private double Speed;
   private boolean ReadyForTakeOff;

   public Plane(String callSign, String model, String company,
         Position position)
   {
      this.CallSign = callSign;
      this.Model = model;
      this.Company = company;
      // this.FlightPlan = flightPlan;
      this.Position = position;
      this.Target = null;
      this.Route = null;
      this.PlaneState = new LandedState();
   }

   public Position getTarget()
   {
      return Target;
   }

   public synchronized void movePlane()
   {
      if (PlaneState instanceof LandedState
            || PlaneState instanceof BoardingState)
      {
         boolean targetReached = Position.movePosition(Target, Speed);
         if (targetReached)
         {
            Route.remove(0);
            if (Route.isEmpty())
            {
               setState(new LandedState());
               this.Speed = 0;
            }
            else
            {
               Target.setPosition(Route.get(0));
            }
         }
      }

   }

   public PlaneState getPlaneState()
   {
      return PlaneState;
   }

   public void setState(PlaneState PlaneState)
   {
      this.PlaneState = PlaneState;
   }

   public void setSpeed(double Speed)
   {
      this.Speed = Speed;
   }

   public synchronized void setRoute(ArrayList<Position> Route)
   {
      this.Route = Route;
      this.Target = Route.get(0);
      this.PlaneState = new TaxiState();
   }

   public void stopPlane()
   {
      this.PlaneState = new LandedState();
      this.Speed = 0;
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

   public boolean isReadyForTakeOff()
   {
      return ReadyForTakeOff;
   }

   public void setReadyForTakeOff(boolean ReadyForTakeOff)
   {
      this.ReadyForTakeOff = ReadyForTakeOff;
   }
   
   public PlaneDTO convertToDTO()
   {
      return new PlaneDTO(this.CallSign,this.PlaneState,this.Position);
   }

/*
 * public FlightPlan getFlightPlan() { return FlightPlan; }
 */

   public Position getPosition()
   {
      return Position;
   }

}
