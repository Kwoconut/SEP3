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
   }

   public void sendPlane(Plane plane) throws RemoteException
   {
      int nr = clients.size() - 1;
      clients.get(nr).getPlaneFromServer(plane);
   }
   @Override
   public void getGroundPlanes(RIClient client) throws RemoteException 
   {
	   client.getGroundPlanesFromServer(model.getGroundPlanes());
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

   public void execute() throws IOException
   {
      System.out.println("Starting socket part");
      System.out.println("Waiting for clients ...");
      Socket socket = new Socket("10.152.194.82", 200);
      Thread t = new Thread(new ServerSocketHandler(model, socket));
      t.start();
      SimulationState simulationState = new SimulationState(this);
      Thread SimulationState = new Thread(simulationState);
      SimulationState.start();
      PlaneDispatcher planeDispatcher = new PlaneDispatcher(this);
      Thread PlaneDispatcher = new Thread(planeDispatcher);
      PlaneDispatcher.start();
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
