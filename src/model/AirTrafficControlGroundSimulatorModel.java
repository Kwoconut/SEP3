package model;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;
import client.IClient;

public class AirTrafficControlGroundSimulatorModel
      implements AirTrafficControlGroundSimulatorModelClientHandler,
      AirTrafficControlGroundSimulator, Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 3218559717712719996L;
   private ArrayList<Plane> planes;
   private AirportGraph airportGraph;
   private IClient client;

   public AirTrafficControlGroundSimulatorModel()
   {
      planes = new ArrayList<Plane>();
      airportGraph = new AirportGraph();
   }

   @Override
   public void getPlanesFromServer(ArrayList<Plane> planes)
   {
      this.planes = planes;
      System.out.println("Client got plane list from server");
   }

   @Override
   public void setClient(Client client)
   {
      this.client = client;
   }

   public AirportGraph getAirportGraph()
   {
      return airportGraph;
   }

}
