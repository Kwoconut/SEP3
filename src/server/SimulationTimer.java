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
            .getGroundPlanes().size(); i++)
      {
         if (this.simulationManager.getServer().getModel().getGroundPlanes().get(i)
               .getPlaneState() instanceof BoardingState)
         {
            ((BoardingState) this.simulationManager.getServer().getModel()
                  .getGroundPlanes().get(i).getPlaneState()).decrement();
            
            if (((BoardingState) this.simulationManager.getServer().getModel()
                  .getGroundPlanes().get(i).getPlaneState()).getTime()
                  .equals(new Timer(0, 0, 0)))
            {
               this.simulationManager.getServer().getModel().getGroundPlanes().get(i)
               .setReadyForTakeOff(true);
            }      
         }
      }
   }
   
   private void updateEmergencyTimer() throws RemoteException
   {
      for (int i = 0; i < this.simulationManager.getServer().getModel()
            .getAirPlanes().size(); i++)
      {
         if (this.simulationManager.getServer().getModel().getAirPlanes().get(i)
               .getPlaneState() instanceof EmergencyState)
         {
            ((EmergencyState) this.simulationManager.getServer().getModel()
                  .getAirPlanes().get(i).getPlaneState()).decrement();
            
            if (((EmergencyState) this.simulationManager.getServer().getModel()
                  .getAirPlanes().get(i).getPlaneState()).getTime()
                  .equals(new Timer(0, 0, 0)))
            {
             for (int j = 0 ; j < this.simulationManager.getServer().getAirClients().size();j++)
             {
                this.simulationManager.getServer().getAirClients().get(j).simulationFailed();
             }
             for (int j = 0 ; j < this.simulationManager.getServer().getGroundClients().size();j++)
             {
                this.simulationManager.getServer().getGroundClients().get(j).simulationFailed();
             }
             this.simulationManager.exitPlaneDispatcher();
             this.simulationManager.exitSimulationTimer();
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
            updateEmergencyTimer();
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
