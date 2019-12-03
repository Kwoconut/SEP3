package model;

public class Edge
{
   private int FromNodeIndex;
   private int ToNodeIndex;
   private int Length;

   public Edge(int fromNodeIndex, int toNodeIndex, int length)
   {
      this.FromNodeIndex = fromNodeIndex;
      this.ToNodeIndex = toNodeIndex;
      this.Length = length;
   }

   public int getFromNodeIndex()
   {
      return FromNodeIndex;
   }

   public int getToNodeIndex()
   {
      return ToNodeIndex;
   }

   public int getLength()
   {
      return Length;
   }

   public int getNeighbourIndex(int nodeIndex)
   {
      if (this.FromNodeIndex == nodeIndex)
      {
         return this.ToNodeIndex;
      }
      else
      {
         return this.FromNodeIndex;
      }
   }
   
   public String toString()
   {
      return FromNodeIndex + " " + ToNodeIndex + " " + Length;
   }

}
