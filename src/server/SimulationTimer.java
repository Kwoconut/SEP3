package server;

import java.rmi.RemoteException;

import model.BoardingState;
import model.EmergencyState;
import model.Timer;

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
            
            if (((BoardingState) this.simulationManager.getServer().getModel()
                  .getPlanes().get(i).getPlaneState()).getTime()
                  .equals(new Timer(0, 0, 0)))
            {
               this.simulationManager.getServer().getModel().getPlanes().get(i)
               .setReadyForTakeOff(true);
            }
            
         }


      }
   }
   
   private void updateEmergencyTimer()
   {
      for (int i = 0; i < this.simulationManager.getServer().getModel()
            .getPlanes().size(); i++)
      {
         if (this.simulationManager.getServer().getModel().getPlanes().get(i)
               .getPlaneState() instanceof EmergencyState)
         {
            ((EmergencyState) this.simulationManager.getServer().getModel()
                  .getPlanes().get(i).getPlaneState()).decrement();
            
            if (((EmergencyState) this.simulationManager.getServer().getModel()
                  .getPlanes().get(i).getPlaneState()).getTime()
                  .equals(new Timer(0, 0, 0)))
            {
               
            }
            
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
            updateBoardingTimer();
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
