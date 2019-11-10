package model;

import java.util.ArrayList;

public class AirportGraph
{
   private ArrayList<GroundNode> nodes;
   private ArrayList<Edge> edges;

   public AirportGraph()
   {
      generateAirportGraph();

      for (int i = 0; i < edges.size(); i++)
      {

         this.nodes.get(edges.get(i).getFromNodeIndex()).getJointEdges()
               .add(edges.get(i));
         this.nodes.get(edges.get(i).getToNodeIndex()).getJointEdges()
               .add(edges.get(i));
      }
   }

   public ArrayList<GroundNode> getGroundNodes()
   {
      return nodes;
   }

   public ArrayList<GroundNode> calculateShortestDistance(GroundNode startNode,
         GroundNode endNode)
   {

      refreshNodes();

      nodes.stream().filter(node -> node.equals(startNode)).findFirst().get()
            .setDistanceFromSource(0);

      int evaluatedNodeId = startNode.getNodeId();

      for (int i = 0; i < this.nodes.size(); i++)
      {
         ArrayList<Edge> currentNodeEdges = this.nodes.get(evaluatedNodeId)
               .getJointEdges();

         for (int joinedEdge = 0; joinedEdge < currentNodeEdges
               .size(); joinedEdge++)
         {
            int neighbourIndex = currentNodeEdges.get(joinedEdge)
                  .getNeighbourIndex(evaluatedNodeId);

            if (!this.nodes.get(neighbourIndex).isVisited())
            {
               int tentative = this.nodes.get(evaluatedNodeId)
                     .getDistanceFromSource()
                     + currentNodeEdges.get(joinedEdge).getLength();

               if (tentative < nodes.get(neighbourIndex)
                     .getDistanceFromSource())
               {
                  nodes.get(neighbourIndex).setDistanceFromSource(tentative);
                  ArrayList<GroundNode> shortestPath = new ArrayList<GroundNode>(
                        nodes.get(evaluatedNodeId).getShortestPath());
                  shortestPath.add(nodes.get(evaluatedNodeId));
                  nodes.get(neighbourIndex).setShortestPath(shortestPath);
               }
            }
         }
         nodes.get(evaluatedNodeId).setIsVisited(true);
         evaluatedNodeId = evaluateNextNode();

      }

      endNode.addShortDistanceNode(endNode);
      System.out.println("Shortest distance from " + startNode + " to "
            + endNode + " is " + endNode.getDistanceFromSource());
      return endNode.getShortestPath();
   }

   private int evaluateNextNode()
   {
      {
         int storedNodeIndex = 0;
         int storedDist = Integer.MAX_VALUE;

         for (int i = 0; i < this.nodes.size(); i++)
         {
            int currentDist = this.nodes.get(i).getDistanceFromSource();

            if (!this.nodes.get(i).isVisited() && currentDist < storedDist)
            {
               storedDist = currentDist;
               storedNodeIndex = i;
            }
         }

         return storedNodeIndex;
      }
   }

   private void generateAirportGraph()
   {
      Edge[] sampleEdges = { new Edge(8, 9, 4), new Edge(0, 4, 2),
            new Edge(11, 10, 4), new Edge(10, 9, 4), new Edge(13, 12, 8),
            new Edge(12, 10, 4), new Edge(1, 4, 2), new Edge(2, 4, 2),
            new Edge(3, 4, 2), new Edge(4, 5, 16), new Edge(5, 6, 4),
            new Edge(5, 13, 8), new Edge(6, 7, 4), new Edge(6, 11, 4),
            new Edge(7, 8, 4), new Edge(12, 14, 10), new Edge(14, 16, 4),
            new Edge(16, 17, 4), new Edge(17, 18, 4), new Edge(18, 19, 4),
            new Edge(19, 5, 4), new Edge(19, 15, 8), new Edge(15, 14, 8) };

      GroundNode groundNode0 = new GroundNode("Gate A", 0,
            new Position(1095, 770));
      GroundNode groundNode1 = new GroundNode("Gate B", 1,
            new Position(1100, 730));
      GroundNode groundNode2 = new GroundNode("Gate C", 2,
            new Position(1090, 690));
      GroundNode groundNode3 = new GroundNode("Gate D", 3,
            new Position(1070, 660));
      GroundNode groundNode4 = new GroundNode("Main Taxiway", 4,
            new Position(1020, 700));
      GroundNode groundNode5 = new GroundNode("Taxiway Chokepoint", 5,
            new Position(845, 238));
      GroundNode groundNode6 = new GroundNode("Taxiway A2", 6,
            new Position(505, 238));
      GroundNode groundNode7 = new GroundNode("Taxiway A2", 7,
            new Position(335, 238));
      GroundNode groundNode8 = new GroundNode("Auxiliary Taxiway C32", 8,
            new Position(323, 185));
      GroundNode groundNode9 = new GroundNode("Runway 14", 9,
            new Position(330, 114));
      GroundNode groundNode10 = new GroundNode("Runway", 10,
            new Position(520, 114));
      GroundNode groundNode11 = new GroundNode("Auxiliary Taxiway C33", 11,
            new Position(500, 185));
      GroundNode groundNode12 = new GroundNode("Runway", 12,
            new Position(800, 114));
      GroundNode groundNode13 = new GroundNode("Auxiliary Taxiway C34", 13,
            new Position(828, 185));
      GroundNode groundNode14 = new GroundNode("Runway", 14,
            new Position(1160, 114));
      GroundNode groundNode15 = new GroundNode("Auxiliary Taxiway C35", 15,
            new Position(1175, 185));
      GroundNode groundNode16 = new GroundNode("Runway 25", 16,
            new Position(1300, 114));
      GroundNode groundNode17 = new GroundNode("Auxiliary Taxiway C36", 17,
            new Position(1327, 185));
      GroundNode groundNode18 = new GroundNode("Taxiway A2", 18,
            new Position(1315, 238));
      GroundNode groundNode19 = new GroundNode("Taxiway A2", 19,
            new Position(1170, 238));

      nodes = new ArrayList<GroundNode>();

      nodes.add(groundNode0);
      nodes.add(groundNode1);
      nodes.add(groundNode2);
      nodes.add(groundNode3);
      nodes.add(groundNode4);
      nodes.add(groundNode5);
      nodes.add(groundNode6);
      nodes.add(groundNode7);
      nodes.add(groundNode8);
      nodes.add(groundNode9);
      nodes.add(groundNode10);
      nodes.add(groundNode11);
      nodes.add(groundNode12);
      nodes.add(groundNode13);
      nodes.add(groundNode14);
      nodes.add(groundNode15);
      nodes.add(groundNode16);
      nodes.add(groundNode17);
      nodes.add(groundNode18);
      nodes.add(groundNode19);

      edges = new ArrayList<Edge>();

      for (int i = 0; i < sampleEdges.length; i++)
      {
         edges.add(sampleEdges[i]);
      }
   }

   private void refreshNodes()
   {
      for (GroundNode node : nodes)
      {
         node.setDistanceFromSource(Integer.MAX_VALUE);
         node.setIsVisited(false);
         node.setShortestPath(new ArrayList<GroundNode>());
      }
   }

}
