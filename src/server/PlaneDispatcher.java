package server;

import java.rmi.RemoteException;

public class PlaneDispatcher implements Runnable
{

   private SimulationManager manager;

   PlaneDispatcher(SimulationManager manager)
   {
      this.manager = manager;
   }

   private void sendGroundPlane()
   {

      for (int i = 0; i < 1; i++)
      {
         for (int j = 0; j < manager.getServer().getGroundClients().size(); j++)
         {
            try
            {
               manager.getServer().getModel().getPlanes().get(i).landPlane(
                     manager.getServer().getModel().getLandingNode(
                           manager.getServer().getModel().getWind()));
               manager.getServer().sendGroundPlaneDTO(
                     manager.getServer().getModel().getPlanes().get(i)
                           .convertToDTO(),
                     manager.getServer().getGroundClients().get(j));
            }
            catch (RemoteException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }

         manager.getServer().getModel().addGroundPlane(
               manager.getServer().getModel().getPlanes().get(i));
         try
         {
            //
            //
            //
            Thread.sleep(10000);
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   @Override
   public void run()
   {
      {
         System.out.println("PlaneDispatcher Started");
         sendGroundPlane();
      }

   }
}