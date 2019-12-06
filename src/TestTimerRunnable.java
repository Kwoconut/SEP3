import java.util.ArrayList;

import groundclientmodel.AirTrafficControlGroundSimulator;
import model.LandedState;
import model.Plane;
import model.PlaneDTO;
import model.StaticPosition;

public class TestTimerRunnable implements Runnable
{
   private AirTrafficControlGroundSimulator model;
   private ArrayList<PlaneDTO> planes;

   public TestTimerRunnable(AirTrafficControlGroundSimulator model)
   {
      this.model = model;
      planes = new ArrayList<PlaneDTO>();
   }

   private void createDummyData()
   {
      PlaneDTO plane1 = new PlaneDTO("WZZ235", new LandedState(),
            new StaticPosition(500, 500),"bbb");
      PlaneDTO plane2 = new PlaneDTO("TA235", new LandedState(),
            new StaticPosition(800, 800),"asd");
      
      planes.add(plane1);
      planes.add(plane2);

   }

   @Override
   public void run()
   {
      createDummyData();
      try
      {
         for (int i = 0; i < planes.size(); i++)
         {
            model.addPlane(planes.get(i));
            Thread.sleep(2000);
         }
         Thread.sleep(2000);
         model.removePlane(1);
         Thread.sleep(2000);
         model.removePlane(0);
         Thread.sleep(2000);
         model.simulationFailed();
      }
      catch (InterruptedException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

}
