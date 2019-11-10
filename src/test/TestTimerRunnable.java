package test;

import java.util.ArrayList;

import model.AirTrafficControlGroundSimulator;
import model.Plane;
import model.Position;

public class TestTimerRunnable implements Runnable
{
   private AirTrafficControlGroundSimulator model;
   private ArrayList<Plane> planes;

   public TestTimerRunnable(AirTrafficControlGroundSimulator model)
   {
      this.model = model;
   }

   private void createDummyData()
   {
      Plane plane1 = new Plane("WZ2356", "", "", "", null,
            new Position(80, 70));
      Plane plane2 = new Plane("LF21Z3", "", "", "", null,
            new Position(80, 70));
      Plane plane3 = new Plane("YOLO", "", "", "", null, new Position(80, 70));
      Plane plane4 = new Plane("AYYYY", "", "", "", null, new Position(80, 70));

      planes = new ArrayList<Plane>();
      planes.add(plane1);
      planes.add(plane2);
      planes.add(plane3);
      planes.add(plane4);

   }

   @Override
   public void run()
   {
      createDummyData();
      int i = 0;
      while (i < planes.size())
         try
         {
            Thread.sleep(10000);
            model.addPlane(planes.get(i));
            i++;
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

   }

}
