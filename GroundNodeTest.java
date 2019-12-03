package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.GroundNode;
import model.StaticPosition;

public class GroundNodeTest
{
   private GroundNode gn1;
   private GroundNode gn2;
   private StaticPosition sp1;
   private StaticPosition sp2;
   
   @Before
   public void setUp() throws Exception {
      sp1 = new StaticPosition(4.20, 3.14);
      sp2 = new StaticPosition(0, 0);
      gn1 = new GroundNode("GroundNode1", 1, sp1);
      gn2 = new GroundNode(null, 0, sp2);
   }
   
   @Test
   public void testGetName()
   {
      assertEquals("GroundNode1", gn1.getName());
   }
   
   @Test
   public void testGetNameWhenNull()
   {
      assertEquals(null, gn2.getName());
   }
   
   @Test
   public void testGetNodeId()
   {
      assertEquals(1, gn1.getNodeId());
   }
   
   @Test
   public void testGetNodeIdWhenZero()
   {
      assertEquals(0, gn2.getNodeId());
   }
   
   @Test
   public void testGetPosition()
   {
      assertEquals(sp1, gn1.getPosition());
   }
   
   @Test
   public void testGetPositionWhenZero()
   {
      assertEquals(sp2, gn2.getPosition());
   }
   
   @Test
   public void testSetJointEdges()
   {
      fail("Not yet implemented");
   }

   @Test
   public void testSetAndCheckIfVisitedWhenTrue()
   {
      gn1.setIsVisited(true);
      assertEquals(true, gn1.isVisited());
   }
   
   @Test
   public void testSetAndCheckIfVisitedWhenFalse()
   {
      gn1.setIsVisited(false);
      assertEquals(false, gn1.isVisited());
   }
   
   @Test
   public void testSetAndGetDistanceFromSource()
   {
      gn1.setDistanceFromSource(420);
      assertEquals(420, gn1.getDistanceFromSource());
   }
   
   @Test
   public void testSetAndGetDistanceFromSourceWhenZero()
   {
      gn2.setDistanceFromSource(0);
      assertEquals(0, gn2.getDistanceFromSource());
   }
   
   @Test
   public void testAddShortDistanceNode()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testGetShortestPathe()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testSetShortestPath()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testConvertToDTO()
   {
      gn1.convertToDTO();
   }
   
   @Test
   public void testToString()
   {
      gn1.toString();
   }
   
   @Test
   public void testEqualsWhenTrue()
   {
      GroundNode gn3 = new GroundNode("GroundNode1", 1, sp1);
      assertEquals(true, gn1.equals(gn3));
   }
   
   @Test
   public void testEqualsWhenFalse()
   {
      assertEquals(false, gn1.equals(gn2));
   }
   
   
}
