package server;

import java.io.IOException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import airClient.AirRIClient;
import groundClient.GroundRIClient;
import model.PlaneDTO;

public class Server implements GroundRIServerWrite, AirRIServerWrite
{
   private ServerModel model;
   private SimulationManager manager;
   private ArrayList<GroundRIClient> groundClients;
   private ArrayList<AirRIClient> airClients;

   public Server(ServerModel model) throws IOException
   {
      this.model = model;
      groundClients = new ArrayList<GroundRIClient>();
      airClients = new ArrayList<AirRIClient>();
      UnicastRemoteObject.exportObject(this, 0);
      manager = new SimulationManager(this);
   }

   public void addGroundClient(GroundRIClient client) throws RemoteException
   {
      if (groundClients.size() >= 1)
      {
         for (int i = 0; i < model.getGroundPlanes().size(); i++)
         {
            sendGroundPlaneDTO(model.getGroundPlanesDTO().get(i), client);
         }
         System.out.println("Updating planes for next ground clients");
      }
      groundClients.add(client);
      System.out.println(airClients.size());
      System.out.println(groundClients.size());
      System.out.println(airClients.size()+groundClients.size());
      if (airClients.size()+groundClients.size()==1)
      {
         manager.planeDispatcherRun();
      }
   }
   
   public void addAirClient(AirRIClient client) throws RemoteException
   {
      if (airClients.size() >= 1)
      {
         for (int i = 0; i < model.getGroundPlanes().size(); i++)
         {
            sendAirPlaneDTO(model.getAirPlanesDTO().get(i), client);
         }
         System.out.println("Updating planes for next air clients");
      }
      airClients.add(client);
      if (airClients.size()+groundClients.size()==1)
      {
         manager.planeDispatcherRun();
      }
   }

   public ArrayList<GroundRIClient> getGroundClients()
   {
	  return groundClients;
   }
   
   public ArrayList<AirRIClient> getAirClients()
   {
	  return airClients;
   }

   public ServerModel getModel()
   {
      return model;
   }

   @Override
   public void sendGroundPlanesDTO(GroundRIClient client) throws RemoteException
   {
	   client.getGroundPlanesDTOFromServer(model.getGroundPlanesDTO());
   }
   
   @Override
   public void sendAirPlanesDTO(AirRIClient client) throws RemoteException
   {
	   client.getAirPlanesDTOFromServer(model.getGroundPlanesDTO());
   }
   
   public void sendGroundNodesDTO(GroundRIClient client) throws RemoteException
   {
	   client.getGroundNodesDTOFromServer(model.getGroundNodesDTO());
   }
   
   public void sendAirNodesDTO(AirRIClient client) throws RemoteException
   {
	   client.getAirNodesDTOFromServer(model.getGroundNodesDTO());
   }
   
   @Override	
   public void sendGroundWind(GroundRIClient client) throws RemoteException
   {
	   client.getWindFromServer(model.getWind());
   }
   
   @Override	
   public void sendAirWind(AirRIClient client) throws RemoteException
   {
	   client.getWindFromServer(model.getWind());
   }
   
   public void sendGroundPlaneDTO(PlaneDTO plane, GroundRIClient client)
		   throws RemoteException
   {
	   client.getGroundPlaneDTOFromServer(plane);
	   
   }
   
   public void sendAirPlaneDTO(PlaneDTO plane, AirRIClient client)
		   throws RemoteException
   {
	   client.getAirPlaneDTOFromServer(plane);
	   
   }
   
   public void simulationFailed(GroundRIClient client) throws RemoteException
   {
	   client.simulationFailed();
   }
   
   public void airSimulationFailed(AirRIClient client) throws RemoteException
   {
	   client.simulationFailed();
   }

   @Override
   public void changeGroundPlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      model.changeGroundPlaneRoute(callSign, startNodeId, endNodeId);
   }
   
   @Override
   public void changeAirPlaneRoute(String callSign, int startNodeId, int endNodeId)
   {
      model.changeAirPlaneRoute(callSign, startNodeId, endNodeId);
   }

   public void execute() throws IOException
   {
      System.out.println("Starting socket part");
      System.out.println("Waiting for clients ...");
      Socket socket = new Socket("10.152.214.106", 6789);
      Thread t = new Thread(new ServerSocketHandler(model, socket));
      t.start();
      manager.simulationStateRun();
   }

   public static void main(String[] args) throws IOException
   {
      try
      {
         LocateRegistry.createRegistry(1099);
         ServerModel model = new ServerModel();
         Server server = new Server(model);
         GroundServerAccess threadSafeServer = new ThreadSafeServer(server);
         Naming.rebind("server", threadSafeServer);
         System.out.println("Starting RMI part");
         server.execute();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}

