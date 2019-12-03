package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.StaticPosition;

public class StaticPositionTest
{
   private double XCoordinate1 = 3.14;
   private double XCoordinate2 = 0;
   private double YCoordinate1 = 4.20;
   private double YCoordinate2 = 0;
   private StaticPosition sp1;
   private StaticPosition sp2;
   
   @Before
   public void setUp() throws Exception {
      sp1 = new StaticPosition(XCoordinate1, YCoordinate1);
      sp2 = new StaticPosition(XCoordinate2, YCoordinate2);
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testGetXCoordinate()
   {
      assertEquals(3.14, sp1.getXCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testGetXCoordinateWhenZero()
   {
      assertEquals(0, sp2.getXCoordinate());
   }

   @SuppressWarnings("deprecation")
   @Test
   public void testGetYCoordinate()
   {
      assertEquals(4.20, sp1.getXCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testGetYCoordinateWhenZero()
   {
      assertEquals(0, sp2.getYCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSetXCoordinate()
   {
      sp2.setXCoordinate(2);
      assertEquals(2, sp2.getXCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSetXCoordinateToZero()
   {
      sp1.setXCoordinate(0);
      assertEquals(0, sp1.getXCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSetYCoordinate()
   {
      sp2.setYCoordinate(2);
      assertEquals(2, sp2.getYCoordinate());
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void testSetYCoordinateToZero()
   {
      sp1.setYCoordinate(0);
      assertEquals(0, sp1.getYCoordinate());
   }
   
   @Test
   public void testSetPosition()
   {
      sp1.setPosition(sp2);
   }
   
}