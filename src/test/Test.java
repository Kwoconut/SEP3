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

      Edge[] edges = { new Edge(8, 9, 4), new Edge(0, 4, 2),
            new Edge(11, 10, 4), new Edge(10, 9, 4), new Edge(13, 12, 8),
            new Edge(12, 10, 4), new Edge(1, 4, 2), new Edge(2, 4, 2),
            new Edge(3, 4, 2), new Edge(4, 5, 16), new Edge(5, 6, 4),
            new Edge(5, 13, 8), new Edge(6, 7, 4), new Edge(6, 11, 4),
            new Edge(7, 8, 4), new Edge(12, 14, 10), new Edge(14, 16, 4),
            new Edge(16, 17, 4), new Edge(17, 18, 4), new Edge(18, 19, 4),
            new Edge(19, 5, 4), new Edge(19, 15, 8), new Edge(15, 14, 8) };

      GroundNode groundNode0 = new GroundNode("Gate A", 0, new Position(0, 0));
      GroundNode groundNode1 = new GroundNode("Gate B", 1, new Position(0, 0));
      GroundNode groundNode2 = new GroundNode("Gate C", 2, new Position(0, 0));
      GroundNode groundNode3 = new GroundNode("Gate D", 3, new Position(0, 0));
      GroundNode groundNode4 = new GroundNode("Main Taxiway", 4,
            new Position(0, 0));
      GroundNode groundNode5 = new GroundNode("Taxiway Chokepoint", 5,
            new Position(0, 0));
      GroundNode groundNode6 = new GroundNode("Taxiway A2", 6,
            new Position(0, 0));
      GroundNode groundNode7 = new GroundNode("Taxiway A2", 7,
            new Position(0, 0));
      GroundNode groundNode8 = new GroundNode("Auxiliary Taxiway C32", 8,
            new Position(0, 0));
      GroundNode groundNode9 = new GroundNode("Runway 14", 9,
            new Position(0, 0));
      GroundNode groundNode10 = new GroundNode("Runway", 10,
            new Position(0, 0));
      GroundNode groundNode11 = new GroundNode("Auxiliary Taxiway C33", 11,
            new Position(0, 0));
      GroundNode groundNode12 = new GroundNode("Runway", 12,
            new Position(0, 0));
      GroundNode groundNode13 = new GroundNode("Auxiliary Taxiway C34", 13,
            new Position(0, 0));
      GroundNode groundNode14 = new GroundNode("Runway", 14,
            new Position(0, 0));
      GroundNode groundNode15 = new GroundNode("Auxiliary Taxiway C35", 15,
            new Position(0, 0));
      GroundNode groundNode16 = new GroundNode("Runway 25", 16,
            new Position(0, 0));
      GroundNode groundNode17 = new GroundNode("Auxiliary Taxiway C36", 17,
            new Position(0, 0));
      GroundNode groundNode18 = new GroundNode("Taxiway A2", 18,
            new Position(0, 0));
      GroundNode groundNode19 = new GroundNode("Taxiway A2", 19,
            new Position(0, 0));

      ArrayList<GroundNode> groundNodes = new ArrayList<GroundNode>();

      groundNodes.add(groundNode0);
      groundNodes.add(groundNode1);
      groundNodes.add(groundNode2);
      groundNodes.add(groundNode3);
      groundNodes.add(groundNode4);
      groundNodes.add(groundNode5);
      groundNodes.add(groundNode6);
      groundNodes.add(groundNode7);
      groundNodes.add(groundNode8);
      groundNodes.add(groundNode9);
      groundNodes.add(groundNode10);
      groundNodes.add(groundNode11);
      groundNodes.add(groundNode12);
      groundNodes.add(groundNode13);
      groundNodes.add(groundNode14);
      groundNodes.add(groundNode15);
      groundNodes.add(groundNode16);
      groundNodes.add(groundNode17);
      groundNodes.add(groundNode18);
      groundNodes.add(groundNode19);
      ArrayList<Edge> edges2 = new ArrayList<Edge>();

      for (int i = 0; i < edges.length; i++)
      {
         edges2.add(edges[i]);
      }

      AirportGraph graph = new AirportGraph(groundNodes, edges2);

      System.out.println(
            graph.calculateShortestDistance(groundNode16, groundNode3));

   }

}
