package test;

import java.util.ArrayList;

import model.AirTrafficControlGroundSimulatorModel;
import model.AirportGraph;
import model.Edge;
import model.GroundNode;
import model.Plane;
import model.Position;

public class Test
{

   public static void main(String[] args) throws InterruptedException
   {

      AirTrafficControlGroundSimulatorModel model = new AirTrafficControlGroundSimulatorModel();

      ArrayList<Position> positions = new ArrayList<Position>();
      Position position1 = new Position(20, 20);
      Position position2 = new Position(100, 100);
      Position position3 = new Position(634, 1000);
      positions.add(position1);
      positions.add(position2);
      positions.add(position3);

      Plane plane = new Plane("WZ2345", "Airbus 700", "Wizz Air", "LANDED",
            new Position(500, 500), 5);

      plane.setRoute(positions);
      while (true)
      {
         for (int i = 0; i < 1; i++)
         {
            plane.movePlane();
         }
         Thread.sleep(0250);
      }

   }

}
