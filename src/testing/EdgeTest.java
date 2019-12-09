package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Edge;

public class EdgeTest
{
   private Edge edge;
   private Edge edge2;

   @Before
   public void setUp() throws Exception
   {
      edge = new Edge(4, 20, 314);
      edge2 = new Edge(0, 0, 0);
   }
     
   @Test
   public void testGetFromNodeIndex()
   {
      assertEquals(4, edge.getFromNodeIndex());
   }
   
   @Test
   public void testGetFromNodeIndexWhenZero()
   {
    assertEquals(0, edge2.getFromNodeIndex());
   }
   
   @Test
   public void testGetToNodeIndex()
   {
      assertEquals(20, edge.getToNodeIndex());
   }
   
   @Test
   public void testGetToNodeIndexWhenZero()
   {
      assertEquals(0, edge2.getToNodeIndex());
   }
   
   @Test
   public void testGetLenght()
   {
      assertEquals(314, edge.getLength());
   }
   
   @Test
   public void testGetLenghtWhenZero()
   {
      assertEquals(0, edge2.getLength());
   }
   
   @Test
   public void testGetNeighbourIndex()
   {
      int nodeIndex = 42;
      assertEquals(42, edge.getNeighbourIndex(nodeIndex));
   }
   
   @Test
   public void testGetNeighbourIndexWhenZero()
   {
      int nodeIndex = 0;
      assertEquals(0, edge2.getNeighbourIndex(nodeIndex));
   }
   
}
