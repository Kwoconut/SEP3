package test;

import java.util.ArrayList;

import model.AirportGraph;
import model.Edge;
import model.GroundNode;
import model.Position;

public class Test
{

   public static void main(String[] args)
   {

      AirportGraph graph = new AirportGraph();

      System.out.println(graph.calculateShortestDistance(
            graph.getGroundNodes().get(0), graph.getGroundNodes().get(16)));

   }

}
