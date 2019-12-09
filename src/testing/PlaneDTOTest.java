package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.InAirState;
import model.PlaneDTO;
import model.PlaneState;
import model.StaticPosition;

public class PlaneDTOTest
{
   private String callsign1;
   private String callsign2;
   private StaticPosition sp1;
   private StaticPosition sp2;
   private PlaneState state1;
   private PlaneState state2;
   private PlaneDTO planeDTO1;
   private PlaneDTO planeDTO2;

   @Before
   public void setUp() throws Exception {
      callsign1 = "Wz3689";
      callsign2 = null;
      sp1 = new StaticPosition(3.14, 4.20);
      sp2 = new StaticPosition(0, 0);
      state1 = new InAirState();
      state2 = null;
      planeDTO1 = new PlaneDTO (callsign1, state1, sp1);
      planeDTO2 = new PlaneDTO (callsign2, state2, sp2);
   }

   @Test
   public void testGetCallSign()
   {
      assertEquals("Wz3689", planeDTO1.getCallSign());
   }
   
   @Test
   public void testGetCallSignWhenNull()
   {
      assertEquals(null, planeDTO2.getCallSign());
   }
   
   @Test
   public void testGetPlaneState()
   {
      assertEquals(state1, planeDTO1.getPlaneState());
   }
   
   @Test
   public void testGetPlaneStateWhenNull()
   {
      assertEquals(null, planeDTO2.getPlaneState());
   }
   
   @Test
   public void testGetPosition()
   {
      assertEquals(sp1, planeDTO1.getPosition());
   }
   
   @Test
   public void testGetPositionWhenZero()
   {
      assertEquals(sp2, planeDTO2.getPosition());
   }
   
}
