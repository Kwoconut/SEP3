package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.GroundNodeDTO;
import model.StaticPosition;

public class GroundNodeDTOTest
{
   private GroundNodeDTO gnDTO1;
   private GroundNodeDTO gnDTO2;
   private int nodeId1;
   private int nodeId2;
   private StaticPosition sp1;
   
   @Before
   public void setUp() throws Exception {
      sp1 = new StaticPosition(420, 15);
      nodeId1 = 1;
      nodeId2 = 0;
      gnDTO1 = new GroundNodeDTO(nodeId1, sp1);
      gnDTO2 = new GroundNodeDTO(nodeId2, null);
   }

   @Test
   public void testGetNodeId()
   {
      assertEquals(1, gnDTO1.getNodeId());
   }
   
   @Test
   public void testGetNodeIdWhenZero()
   {
      assertEquals(0, gnDTO2.getNodeId());
   }
   
   @Test
   public void testGetPosition()
   {
      assertEquals(sp1, gnDTO1.getPosition());
   }
   
   @Test
   public void testGetPositionWhenNull()
   {
      assertEquals(null, gnDTO2.getPosition());
   }

}
