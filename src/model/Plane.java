package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Plane implements Serializable
{
   private String CallSign;
   private String Model;
   private String Company;
//   private FlightPlan FlightPlan;
   private PlaneState PlaneState;
   private MovingPosition Position;
   private StaticPosition Target;
   private ArrayList<Node> Route;
   private double Speed;
   private boolean ReadyForTakeOff;

   public Plane(String callSign, String model, String company,
         MovingPosition position, StaticPosition target)
   {
      this.CallSign = callSign;
      this.Model = model;
      this.Company = company;
      // this.FlightPlan = flightPlan;
      this.Position = position;
      this.Target = target;
      this.Route = null;
      this.PlaneState = new LandedState();
      this.ReadyForTakeOff = true;
   }

   public double getSpeed()
   {
      return Speed;
   }

   public StaticPosition getTarget()
   {
      return Target;
   }

   public synchronized void movePlane()
   {
      if (!(PlaneState instanceof LandedState
            || PlaneState instanceof BoardingState))
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
            else if (Route.get(0).getNodeId() == 9)
            {
               Route.add(new Node("Exit point EAST", 20, new StaticPosition(1550, 115)));
               Target.setPosition(Route.get(0).getPosition());
            }
            else if (Route.get(0).getNodeId() == 16)
            {
               Route.add(new Node("Exit point WEST ", 21, new StaticPosition(0, 115)));
               Target.setPosition(Route.get(0).getPosition());
            }
            else if (Route.get(0).getNodeId() == 20
                  || Route.get(0).getNodeId() == 21)
            {
               Target.setPosition(Route.get(0).getPosition());
            }
            else
            {
               Target.setPosition(Route.get(0).getPosition());
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

   public ArrayList<Node> getRoute()
   {
      return Route;
   }

   public void setRoute(ArrayList<Node> Route)
   {
      this.Speed = 2;
      this.Route = Route;
      this.Target = Route.get(0).getPosition();
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
      String route = "";
      try {
         route = Route.get(Route.size()-1).getName();
      }
      catch (IndexOutOfBoundsException e)
      {
         route = "No target";
      }
      return new PlaneDTO(this.CallSign, this.PlaneState, this.Position,
            route);
   }

/*
 * public FlightPlan getFlightPlan() { return FlightPlan; }
 */

   public StaticPosition getPosition()
   {
      return this.Position;
   }

   public void landPlane(Node node)
   {
      if (node.getNodeId() == 9)
      {
         this.Position.setPosition(new StaticPosition(1550, 114));
      }
      else
      {
         this.Position.setPosition(new StaticPosition(0, 114));
      }

      ArrayList<Node> Route = new ArrayList<Node>();
      setState(new LandingState());
      this.Speed = 10;
      Route.add(node);
      this.Target = Route.get(0).getPosition();
      this.Route = Route;
   }

}
