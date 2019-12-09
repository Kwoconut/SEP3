package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.InAirState;
import model.MovingPosition;
import model.Plane;
import model.PlaneState;
import model.StaticPosition;

public class PlaneTest
{
   private Plane plane1;
   private Plane plane2;
   private Plane plane3;
   private MovingPosition mpos1;
   private MovingPosition mpos2;
   private MovingPosition mpos3;
   private StaticPosition spos1;
   private StaticPosition spos2;
   private StaticPosition spos3;
   
   @Before
   public void setUp() throws Exception {
      mpos1 = new MovingPosition(3.14, 4.20);
      mpos2 = new MovingPosition(14.57, 23.31);
      mpos3 = new MovingPosition(0, 0);
      spos1 = new StaticPosition(4.2, 3.14);
      spos2 = new StaticPosition(52.73, 123.42);
      spos3 = new StaticPosition(0, 0);
      plane1 = new Plane("Wz3689", "Airbus A700", "Wizz Air", mpos1, spos1);
      plane2 = new Plane("Ta3759", "Boeing 777", "Turkish Airlines", mpos2, spos2);
      plane3 = new Plane(null, null, null, mpos3, spos3);
   }
   
   @Test
   public void testGetTarget()
   {
      assertEquals(spos1, plane1.getTarget());
   }
   
   @Test
   public void testGetTargetWhenNull()
   {
      assertEquals(spos3, plane3.getTarget());
   }
   
   @Test
   public void testMovePlane()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testSetAndGetPlaneState()
   {
      PlaneState planeState = new InAirState();
      plane1.setState(planeState);
      assertEquals("In Air", plane1.getPlaneState().toString());
   }
   
   @Test
   public void testSetAndGetPlaneStateWhenNull()
   {
     plane3.setState(null);
     assertEquals(null, plane3.getPlaneState());
   }
   
   @Test
   public void testSetPlaneSpeed()
   {
      plane2.setSpeed(15234.232);
   }
   
   @Test
   public void testSetPlaneSpeedToZero()
   {
      plane3.setSpeed(0);
   }
   
   @Test
   public void testSetRouteWhenNull()
   {
      plane1.setRoute(null);
   }
   
   @Test
   public void testStopPlane()
   {
      plane2.stopPlane();
   }
   
   @Test
   public void testGetCallSign()
   {
      assertEquals("Wz3689", plane1.getCallSign());
   }
   
   @Test
   public void testGetCallSignWhenNull()
   {
      assertEquals(null, plane3.getCallSign());
   }
   
   @Test
   public void testGetModel()
   {
      assertEquals("Boeing 777", plane2.getCallSign());
   }
   
   @Test
   public void testGetModelWhenNull()
   {
      assertEquals(null, plane3.getCallSign());
   }
   
   @Test
   public void testGetCompany()
   {
      assertEquals("Turkish Airlines", plane2.getCompany());
   }
   
   @Test
   public void testGetCompanyWhenNull()
   {
      assertEquals(null, plane3.getCompany());
   }
   
   @Test
   public void setReadyForTakeOffTrue()
   {
      plane1.setReadyForTakeOff(true);
   }
   
   @Test
   public void setReadyForTakeOffFalse()
   {
      plane2.setReadyForTakeOff(false);
   }
   
   @Test
   public void testIsReadyForTakeOffTrue()
   {
      plane1.setReadyForTakeOff(true);
      assertEquals(true, plane1.isReadyForTakeOff());
   }
   
   @Test
   public void testIsReadyForTakeOffFalse()
   {
      plane2.setReadyForTakeOff(false);
      assertEquals(false, plane2.isReadyForTakeOff());
   }
   
   @Test
   public void testConvertToDTO()
   {
      plane1.convertToDTO();
   }
   
   @Test
   public void testConvertToDTOWhenDataIsNull()
   {
      plane3.convertToDTO();
   }
   
   @Test
   public void testGetPosition()
   {
      assertEquals(mpos1, plane1.getPosition());
   }
   
   @Test
   public void testGetPositionWhenZero()
   {
      assertEquals(mpos3, plane3.getPosition());
   }

}
