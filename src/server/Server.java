package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.RIClient;
import model.Plane;

public class Server implements RemoteServer
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
      PlanesDispatcher planeDispatcher = new PlanesDispatcher(this);
      Thread PlanesSender = new Thread(planeDispatcher);
      PlanesSender.start();
   }

   public void sendPlane(Plane plane) throws RemoteException
   {
      int nr = clients.size() - 1;
      clients.get(nr).getPlaneFromServer(plane);
   }

   public ServerModel getModel()
   {
      return model;
   }

   public void execute() throws IOException
   {
      System.out.println("Starting socket part");
      System.out.println("Waiting for clients ...");
      Socket socket = new Socket("10.152.194.82", 200);
      Thread t = new Thread(new ServerSocketHandler(model, socket));
      t.start();
   }

   public static void main(String[] args) throws IOException
   {
      try
      {
         LocateRegistry.createRegistry(1099);
         ServerModel model = new ServerModel();
         RemoteServer server = new Server(model);
         Naming.rebind("server", server);
         System.out.println("Starting RMI part");
         server.execute();

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
