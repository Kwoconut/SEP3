package model;

import java.util.ArrayList;

public class GroundNode
{
   private String Name;
   private int DistanceFromSource = Integer.MAX_VALUE;
   private int NodeId;
   private boolean IsVisited;
   private StaticPosition Position;
   private ArrayList<Edge> Edges;
   private ArrayList<GroundNode> ShortestPath;

   public GroundNode(String name, int nodeId, StaticPosition staticPosition)
   {
      this.Name = name;
      this.NodeId = nodeId;
      this.Position = staticPosition;
      this.Edges = new ArrayList<Edge>();
      this.ShortestPath = new ArrayList<GroundNode>();
   }

   public String getName()
   {
      return Name;
   }

   public int getNodeId()
   {
      return NodeId;
   }

   public StaticPosition getPosition()
   {
      return Position;
   }

   public ArrayList<Edge> getJointEdges()
   {
      return Edges;
   }

   public void setJointEdges(ArrayList<Edge> jointEdges)
   {
      this.Edges = jointEdges;
   }

   public boolean isVisited()
   {
      return IsVisited;
   }

   public void setIsVisited(boolean isVisited)
   {
      this.IsVisited = isVisited;
   }

   public void setDistanceFromSource(int distanceFromSource)
   {
      this.DistanceFromSource = distanceFromSource;
   }

   public int getDistanceFromSource()
   {
      return DistanceFromSource;
   }

   public void addShortDistanceNode(GroundNode node)
   {
      this.ShortestPath.add(node);
   }

   public ArrayList<GroundNode> getShortestPath()
   {
      return ShortestPath;
   }

   public void setShortestPath(ArrayList<GroundNode> shortestPath)
   {
      this.ShortestPath = shortestPath;
   }

   public GroundNodeDTO convertToDTO()
   {
      return new GroundNodeDTO(this.NodeId, this.Position);
   }

   public String toString()
   {
      String s = "";

      s += Name + "  ";
      s += NodeId;

      return s;
   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof GroundNode))
      {
         return false;
      }
      GroundNode other = (GroundNode) obj;
      return other.getNodeId() == this.NodeId;
   }
}
