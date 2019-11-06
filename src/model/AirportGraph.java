package model;

import java.util.ArrayList;

public class AirportGraph
{
   private ArrayList<GroundNode> nodes;
   private ArrayList<Edge> edges;

   public AirportGraph(ArrayList<GroundNode> nodes, ArrayList<Edge> edges)
   {
      this.nodes = nodes;
      this.edges = edges;

      for (int i = 0; i < edges.size(); i++)
      {

         this.nodes.get(edges.get(i).getFromNodeIndex()).getJointEdges()
               .add(edges.get(i));
         this.nodes.get(edges.get(i).getToNodeIndex()).getJointEdges()
               .add(edges.get(i));
      }
   }

   public ArrayList<GroundNode> calculateShortestDistance(GroundNode startNode,
         GroundNode endNode)
   {
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

/*
 * if (!this.nodes[neighbourIndex].isVisited()) { int tentative =
 * this.nodes[nextNode].getDistanceFromSource() +
 * currentNodeEdges.get(joinedEdge).getLength(); if (tentative <
 * nodes[neighbourIndex].getDistanceFromSource()) {
 * nodes[neighbourIndex].setDistanceFromSource(tentative); ArrayList<Node>
 * shortestPath = new ArrayList<>(nodes[nextNode].getShortestPath());
 * shortestPath.add(nodes[nextNode]);
 * nodes[neighbourIndex].setShortestPath(shortestPath); } } }
 * nodes[nextNode].setIsVisited(true); nextNode = getNodeShortestDistance();
 */

}
