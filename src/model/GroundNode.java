package model;

import java.util.ArrayList;

public class GroundNode
{
   private String name;
   private int distanceFromSource = Integer.MAX_VALUE;
   private int nodeId;
   private boolean isVisited;
   private StaticPosition staticPosition;
   private ArrayList<Edge> jointEdges;
   private ArrayList<GroundNode> shortestPath;

   public GroundNode(String name, int nodeId, StaticPosition staticPosition)
   {
      this.name = name;
      this.nodeId = nodeId;
      this.staticPosition = staticPosition;
      this.jointEdges = new ArrayList<Edge>();
      this.shortestPath = new ArrayList<GroundNode>();
   }

   public String getName()
   {
      return name;
   }

   public int getNodeId()
   {
      return nodeId;
   }

   public StaticPosition getPosition()
   {
      return staticPosition;
   }

   public ArrayList<Edge> getJointEdges()
   {
      return jointEdges;
   }

   public void setJointEdges(ArrayList<Edge> jointEdges)
   {
      this.jointEdges = jointEdges;
   }

   public boolean isVisited()
   {
      return isVisited;
   }

   public void setIsVisited(boolean isVisited)
   {
      this.isVisited = isVisited;
   }

   public void setDistanceFromSource(int distanceFromSource)
   {
      this.distanceFromSource = distanceFromSource;
   }

   public int getDistanceFromSource()
   {
      return distanceFromSource;
   }

   public void addShortDistanceNode(GroundNode node)
   {
      this.shortestPath.add(node);
   }

   public ArrayList<GroundNode> getShortestPath()
   {
      return shortestPath;
   }

   public void setShortestPath(ArrayList<GroundNode> shortestPath)
   {
      this.shortestPath = shortestPath;
   }

   public GroundNodeDTO convertToDTO()
   {
      return new GroundNodeDTO(this.nodeId, this.staticPosition);
   }

   public String toString()
   {
      String s = "";

      s += name;

      return s;
   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof GroundNode))
      {
         return false;
      }
      GroundNode other = (GroundNode) obj;
      return other.getNodeId() == this.nodeId;
   }
}
