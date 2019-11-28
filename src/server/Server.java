package server;

import java.io.IOException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.Client;
import client.RIClient;
import model.Plane;
import model.PlaneDTO;

public class Server implements RIServerWrite
{
   private ServerModel model;
   private ArrayList<RIClient> clients;

   public Server(ServerModel model) throws IOException
   {
      this.model = model;
      clients = new ArrayList<RIClient>();
      UnicastRemoteObject.exportObject(this, 0);
   }

   public void addClient(RIClient client) throws RemoteException
   {
      clients.add(client);
      if(clients.size()==1)
      {
      PlaneDispatcher planeDispatcher = new PlaneDispatcher(this);
      Thread PlaneDispatcher = new Thread(planeDispatcher);
      PlaneDispatcher.start();
      }
   }

   public void sendPlaneDTO(PlaneDTO plane,RIClient client) throws RemoteException
   {
      client.getPlaneDTOFromServer(plane);
      System.out.println("plane sent to client");
      System.out.println(plane.getCallSign());
      
   }
   @Override
   public void getGroundPlanesDTO(RIClient client) throws RemoteException 
   {
	   client.getGroundPlanesDTOFromServer(model.getGroundPlanesDTO());
   }

   public void simulationFailed(RIClient client) throws RemoteException 
   {
	   client.simulationFailed();
   }
   
   public ServerModel getModel()
   {
      return model;
   }
   
   public ArrayList<RIClient> getClients()
   {
	   return clients;
   }

   @Override
   public void changePlaneRoute(String callSign, int startNodeId, int endNodeId) 
   {
	   model.changePlaneRoute(callSign,startNodeId,endNodeId);
   }
   
   public void execute() throws IOException
   {
      System.out.println("Starting socket part");
      System.out.println("Waiting for clients ...");
      System.out.println("a");
      Socket socket = new Socket("10.152.218.105", 6789);
      System.out.println("s");
      Thread t = new Thread(new ServerSocketHandler(model,socket));
      System.out.println("sss");
      t.start();
      System.out.println("ssss");
      SimulationState simulationState = new SimulationState(this);
      Thread SimulationState = new Thread(simulationState);
      SimulationState.start();
   }

   public static void main(String[] args) throws IOException
   {
      try
      {
         LocateRegistry.createRegistry(1099);
         ServerModel model = new ServerModel();
         Server server = new Server(model);
         ServerAccess threadSafeServer = new ThreadSafeServer(server);
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
