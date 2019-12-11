package server;

import java.rmi.RemoteException;

import model.BoardingState;
import model.LandedState;
import model.StaticPosition;
import model.TakeoffState;
import model.TaxiState;

public class SimulationState implements Runnable
{
   private SimulationManager manager;
   private ServerModel model;

   SimulationState(SimulationManager manager, ServerModel model)
   {
      this.manager = manager;
      this.model = model;
   }

   private synchronized void takeOffPlanes() throws RemoteException
   {
      for (int i = 0; i < model.getSimulationGroundPlanes().size(); i++)
      {
         if (model.getSimulationGroundPlanes().get(i).getPosition()
               .equals(new StaticPosition(1560, 115)))
         {
            model.getSimulationAirPlanes().add(model.getSimulationGroundPlanes().get(i));
            model.getSimulationGroundPlanes().remove(i);
            for (int j = 0; j < manager.getServer().getGroundClients()
                  .size(); j++)
            {
               manager.getServer().removeGroundPlane(
                     manager.getServer().getGroundClients().get(j), i);
            }
         }

         if (model.getSimulationGroundPlanes().get(i).getPosition()
               .equals(new StaticPosition(-10, 115)))
         {
            model.getSimulationAirPlanes().add(model.getSimulationGroundPlanes().get(i));
            model.getSimulationGroundPlanes().remove(i);
            for (int j = 0; j < manager.getServer().getGroundClients()
                  .size(); j++)
            {
               manager.getServer().removeGroundPlane(
                     manager.getServer().getGroundClients().get(j), i);
            }
         }
      }
   }

   // 956 486
   private synchronized void landPlanes() throws RemoteException
   {
      for (int i = 0; i < model.getSimulationAirPlanes().size(); i++)
      {
         if (model.getSimulationAirPlanes().get(i).getPosition()
               .equals(new StaticPosition(956, 486)))
         {
            model.getSimulationAirPlanes().get(i)
                  .landPlane(model.getLandingNode(model.getWind()));
            model.getSimulationGroundPlanes().add(model.getSimulationAirPlanes().get(i));
            for (int j = 0; j < manager.getServer().getGroundClients()
                  .size(); j++)
            {
               manager.getServer().sendGroundPlaneDTO(
                     model.getSimulationAirPlanes().get(i).convertToDTO(),
                     manager.getServer().getGroundClients().get(j));
            }
            for (int j = 0; j < manager.getServer().getAirClients().size(); j++)
            {
               manager.getServer().removeAirPlane(
                     manager.getServer().getAirClients().get(j), i);
            }
            model.getSimulationAirPlanes().remove(i);
            i = model.getSimulationAirPlanes().size();
         }
      }
   }

   private synchronized void updateStateOnGroundLocation()
   {
      for (int i = 0; i < model.getSimulationGroundPlanes().size(); i++)
      {
         if (!model.getSimulationGroundPlanes().get(i).isReadyForTakeOff())
         {
            for (int j = 0; j < model.getGateNodes().size(); j++)
            {
               if (model.getGateNodes().get(j).getPosition()
                     .equals(model.getSimulationGroundPlanes().get(i).getPosition())
                     && !(model.getSimulationGroundPlanes().get(i)
                           .getPlaneState() instanceof BoardingState))
               {
                  model.getSimulationGroundPlanes().get(i).setState(new BoardingState());
                  model.getSimulationGroundPlanes().get(i).setSpeed(0);
               }
            }
         }
         else
         {
            for (int j = 0; j < model.getGateNodes().size(); j++)
            {
               if (!(model.getSimulationGroundPlanes().get(i)
                     .getPlaneState() instanceof TaxiState))
               {
                  if (model.getGateNodes().get(j).getPosition()
                        .equals(model.getSimulationGroundPlanes().get(i).getPosition())
                        && !(model.getSimulationGroundPlanes().get(i)
                              .getPlaneState() instanceof LandedState))
                  {
                     model.getSimulationGroundPlanes().get(i).setState(new LandedState());
                     model.getSimulationGroundPlanes().get(i).setSpeed(0);
                  }
               }
            }

            for (int j = 0; j < model.getTakeoffNodes().size(); j++)
            {

               if (model.getSimulationGroundPlanes().get(i).getPosition()
                     .equals(model.getTakeoffNodes().get(j).getPosition()))
               {
                  model.getSimulationGroundPlanes().get(i).setState(new TakeoffState());
                  model.getSimulationGroundPlanes().get(i).setSpeed(10);

               }
            }
         }
      }
   }

   private synchronized void movePlanes() throws RemoteException
   {
      for (int i = 0; i < model.getSimulationGroundPlanes().size(); i++)
      {
         model.getSimulationGroundPlanes().get(i).movePlane();
      }
      for (int i = 0; i < model.getSimulationAirPlanes().size(); i++)
      {
         model.getSimulationAirPlanes().get(i).movePlane();
      }

      for (int i = 0; i < manager.getServer().getGroundClients().size(); i++)
      {
         manager.getServer().sendGroundPlanesDTO(
               manager.getServer().getGroundClients().get(i));
      }

      for (int i = 0; i < manager.getServer().getAirClients().size(); i++)
      {
         manager.getServer()
               .sendAirPlanesDTO(manager.getServer().getAirClients().get(i));
      }
   }

// to be changed
   private synchronized boolean checkCollision() throws RemoteException
   {
      for (int i = 0; i < model.getSimulationGroundPlanes().size(); i++)
      {
         for (int j = i + 1; j < model.getSimulationGroundPlanes().size(); j++)
         {
            if (model.getSimulationGroundPlanes().get(i).getPosition()
                  .equals(model.getSimulationGroundPlanes().get(j).getPosition()))
            {
               return true;
            }
         }
      }
      
      for (int i = 0; i < model.getSimulationAirPlanes().size(); i++)
      {
         for (int j = i + 1; j < model.getSimulationAirPlanes().size(); j++)
         {
            if (model.getSimulationAirPlanes().get(i).getPosition()
                  .equals(model.getSimulationAirPlanes().get(j).getPosition()))
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public void run()
   {
      System.out.println("SimulationState started");
      while (true)
      {
            try
            {

               movePlanes();
               updateStateOnGroundLocation();
               takeOffPlanes();
               landPlanes();
            }
            catch (RemoteException e)
            {
               e.printStackTrace();
            }
         
         if (model.getSimulationGroundPlanes().size() >= 2)
         {
            try
            {
               if (checkCollision())
               {
                  manager.exitPlaneDispatcher();
                  manager.exitSimulationTimer();
                  System.out.println("Simulation State Thread Stopped");
                  break;
               }
            }
            catch (RemoteException e)
            {
               e.printStackTrace();
            }
         }
         try
         {
            Thread.sleep(0100);
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      for (int x = 0; x < manager.getServer().getGroundClients().size(); x++)
      {
         try
         {
            manager.getServer().simulationFailed(
                  manager.getServer().getGroundClients().get(x));
         }
         catch (RemoteException e)
         {
            e.printStackTrace();
         }
      }
      for (int x = 0; x < manager.getServer().getAirClients().size(); x++)
      {
         try
         {
            manager.getServer().airSimulationFailed(
                  manager.getServer().getAirClients().get(x));
         }
         catch (RemoteException e)
         {
            e.printStackTrace();
         }
      }
   }

}
