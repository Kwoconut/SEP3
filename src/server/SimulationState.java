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

   SimulationState(SimulationManager manager)
   {
      this.manager = manager;
   }

   private void takeOffPlanes() throws RemoteException
   {
      for (int i = 0; i < manager.getServer().getModel().getGroundPlanes()
            .size(); i++)
      {
         if (manager.getServer().getModel().getGroundPlanes().get(i)
               .getPosition().equals(new StaticPosition(1560, 115)))
         {
            manager.getServer().getModel().getAirPlanes()
                  .add(manager.getServer().getModel().getGroundPlanes().get(i));
            manager.getServer().getModel().getGroundPlanes().remove(i);
            for (int j = 0; j < manager.getServer().getGroundClients()
                  .size(); j++)
            {
               manager.getServer().removeGroundPlane(
                     manager.getServer().getGroundClients().get(j), i);
            }
         }

         if (manager.getServer().getModel().getGroundPlanes().get(i)
               .getPosition().equals(new StaticPosition(-10, 115)))
         {
            manager.getServer().getModel().getAirPlanes()
                  .add(manager.getServer().getModel().getGroundPlanes().get(i));
            manager.getServer().getModel().getGroundPlanes().remove(i);
            for (int j = 0; j < manager.getServer().getGroundClients()
                  .size(); j++)
            {
               manager.getServer().removeGroundPlane(
                     manager.getServer().getGroundClients().get(j), i);
            }
         }
      }
   }

   private void updateStateOnLocation()
   {
      for (int i = 0; i < manager.getServer().getModel().getGroundPlanes()
            .size(); i++)
      {
         if (!manager.getServer().getModel().getGroundPlanes().get(i)
               .isReadyForTakeOff())
         {
            for (int j = 0; j < manager.getServer().getModel().getGateNodes()
                  .size(); j++)
            {
               if (manager.getServer().getModel().getGateNodes().get(j)
                     .getPosition()
                     .equals(manager.getServer().getModel().getGroundPlanes()
                           .get(i).getPosition())
                     && !(manager.getServer().getModel().getGroundPlanes()
                           .get(i).getPlaneState() instanceof BoardingState))
               {
                  manager.getServer().getModel().getGroundPlanes().get(i)
                        .setState(new BoardingState());
                  manager.getServer().getModel().getGroundPlanes().get(i)
                        .setSpeed(0);
               }
            }
         }
         else
         {
            for (int j = 0; j < manager.getServer().getModel().getGateNodes()
                  .size(); j++)
            {
               if (!(manager.getServer().getModel().getGroundPlanes().get(i)
                     .getPlaneState() instanceof TaxiState))
               {
                  if (manager.getServer().getModel().getGateNodes().get(j)
                        .getPosition()
                        .equals(manager.getServer().getModel().getGroundPlanes()
                              .get(i).getPosition())
                        && !(manager.getServer().getModel().getGroundPlanes()
                              .get(i).getPlaneState() instanceof LandedState))
                  {
                     manager.getServer().getModel().getGroundPlanes().get(i)
                           .setState(new LandedState());
                     manager.getServer().getModel().getGroundPlanes().get(i)
                           .setSpeed(0);
                  }
               }
            }

            for (int j = 0; j < manager.getServer().getModel().getTakeoffNodes()
                  .size(); j++)
            {

               if (manager.getServer().getModel().getGroundPlanes().get(i)
                     .getPosition().equals(manager.getServer().getModel()
                           .getTakeoffNodes().get(j).getPosition()))
               {
                  manager.getServer().getModel().getGroundPlanes().get(i)
                        .setState(new TakeoffState());
                  manager.getServer().getModel().getGroundPlanes().get(i)
                        .setSpeed(10);

               }
            }
         }
      }
   }

   private void movePlanes() throws RemoteException
   {
      for (int i = 0; i < manager.getServer().getModel().getGroundPlanes()
            .size(); i++)
      {
         manager.getServer().getModel().getGroundPlanes().get(i).movePlane();
      }
      for (int i = 0; i < manager.getServer().getGroundClients().size(); i++)
      {
         manager.getServer().sendGroundPlanesDTO(
               manager.getServer().getGroundClients().get(i));
      }
      for (int i = 0; i < manager.getServer().getModel().getAirPlanes()
            .size(); i++)
      {
         manager.getServer().getModel().getAirPlanes().get(i).movePlane();
      }
      for (int i = 0; i < manager.getServer().getAirClients().size(); i++)
      {
         manager.getServer()
               .sendAirPlanesDTO(manager.getServer().getAirClients().get(i));
      }
   }

// to be changed
   private boolean checkCollision() throws RemoteException
   {
      for (int i = 0; i < manager.getServer().getModel().getGroundPlanes()
            .size(); i++)
      {
         for (int j = i + 1; j < manager.getServer().getModel()
               .getGroundPlanes().size(); j++)
         {
            if (manager.getServer().getModel().getGroundPlanes().get(i)
                  .getPosition().equals(manager.getServer().getModel()
                        .getGroundPlanes().get(j).getPosition()))
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
         if (manager.getServer().getModel().getGroundPlanes().size() >= 1)
         {
            try
            {
               takeOffPlanes();
               updateStateOnLocation();
               movePlanes();
            }
            catch (RemoteException e)
            {
               e.printStackTrace();
            }
         }
         if (manager.getServer().getModel().getGroundPlanes().size() >= 2)
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
