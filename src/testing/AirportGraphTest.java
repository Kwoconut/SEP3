package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.PathfindingGraph;
import model.Edge;
import model.GroundNode;
import model.StaticPosition;

public class AirportGraphTest
{
   private PathfindingGraph ag;
   private GroundNode gn1;
   private GroundNode gn2;
   private Edge edge1;
   private Edge edge2;
   private StaticPosition sp1;
   private StaticPosition sp2;
   
   @Before
   public void setUp() throws Exception {
      sp1 = new StaticPosition(4.20, 3.14);
      sp2 = new StaticPosition(0, 0);
      edge1 = new Edge(4, 20, 314);
      edge2 = new Edge(0, 0, 0);
      gn1 = new GroundNode("GroundNode1", 1, sp1);
      gn2 = new GroundNode(null, 0, sp2);
      ag = new PathfindingGraph();
      ArrayList<Edge> edges = new ArrayList<Edge>();
      ArrayList<GroundNode> nodes = new ArrayList<GroundNode>();
      edges.add(edge1);
      edges.add(edge2);
      nodes.add(gn1);
      nodes.add(gn2);
   }
   
   @Test
   public void testGetGroundNodes()
   {
      ArrayList<GroundNode> nodes = new ArrayList<GroundNode>();
      nodes.add(gn1);
      nodes.add(gn2);
      assertEquals(nodes, ag.getGroundNodes());
   }
   
   @Test
   public void testCalculateShortestDistance()
   {
      ag.calculateShortestDistance(gn2, gn1);
   }
   
   @Test
   public void testEvaluateNextNode()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testGenerateAirportGraph()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public void testRefreshNodes()
   {
      fail("Not yet implemented");
   }

}
