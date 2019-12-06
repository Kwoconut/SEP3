package server;

import java.rmi.RemoteException;

import model.BoardingState;

public class SimulationTimer implements Runnable
{
   private SimulationManager simulationManager;

   public SimulationTimer(SimulationManager simulationManager)
   {
      this.simulationManager = simulationManager;
   }

   private void updateBoardingTimer()
   {
      for (int i = 0; i < this.simulationManager.getServer().getModel()
            .getPlanes().size(); i++)
      {
         if (this.simulationManager.getServer().getModel().getPlanes().get(i)
               .getPlaneState() instanceof BoardingState)
         {
            ((BoardingState) this.simulationManager.getServer().getModel()
                  .getPlanes().get(i).getPlaneState()).decrement();
         }

      }
   }

   private void sendUpdatedTimer() throws RemoteException
   {
      this.simulationManager.getServer().getModel().incrementTimer();

      for (int i = 0; i < this.simulationManager.getServer().getAirClients()
            .size(); i++)
      {
         this.simulationManager.getServer().getAirClients().get(i)
               .getTimerFromServer(
                     this.simulationManager.getServer().getModel().getTimer());
      }

      for (int i = 0; i < this.simulationManager.getServer().getGroundClients()
            .size(); i++)
      {
         this.simulationManager.getServer().getGroundClients().get(i)
               .getTimerFromServer(
                     this.simulationManager.getServer().getModel().getTimer());
      }
   }

   @Override
   public void run()
   {
      while (true)
      {
         try
         {
            sendUpdatedTimer();
            Thread.sleep(0250);
         }
         catch (RemoteException | InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

   }

}
