package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.MovingPosition;
import model.StaticPosition;

public class MovingPositionTest
{
   private double XCoordinate1 = 4.20;
   private double XCoordinate2 = 0;
   private double XCoordinate3 = 15.2;
   private double YCoordinate1 = 3.14;
   private double YCoordinate2 = 0;
   private double YCoordinate3 = 16.4;
   private StaticPosition sp1;
   private StaticPosition sp2;
   private MovingPosition mp1;
   private MovingPosition mp2;
   private double speed1 = 420.420;
   private double speed2 = 0;

   @Before
   public void setUp() throws Exception {
      sp1 = new StaticPosition(XCoordinate1, YCoordinate1);
      sp2 = new StaticPosition(XCoordinate2, YCoordinate2);
      mp1 = new MovingPosition(XCoordinate3, YCoordinate3);
      mp2 = new MovingPosition(XCoordinate2, YCoordinate2);
   }
   
   @Test
   public void testMovePosition()
   {
     mp1.movePosition(sp1, speed1);
   }
   
   @Test
   public void testMovePositionWhenZero()
   {
     mp2.movePosition(sp2, speed2);
   }

}
