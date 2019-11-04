package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.RIClient;

public class Server implements RemoteServer
{
	private ServerModel model;
	private ArrayList<RIClient> clients;
	
	public Server(ServerModel model) throws RemoteException
	{
		this.model = model;
		clients = new ArrayList<RIClient>();
		UnicastRemoteObject.exportObject(this, 0);
	}
	
	public void addClient(RIClient client) throws RemoteException
	{
		clients.add(client);
		sendPlanes(client);
		sendGroundKL(client);
	}
	
	public void sendPlanes(RIClient sender) throws RemoteException
	{
		sender.getPlanesFromServer(model.getPlanes());
	}
	
	public void sendGroundKL(RIClient sender) throws RemoteException
	{
		sender.getGrundKLFromServer(model.getGroundKeyLocations());
	}
	
	   public static void main(String[] args) throws RemoteException
	   {
	      try
	      {
	         LocateRegistry.createRegistry(1099);
	         ServerModel model = new ServerModel();
	         RemoteServer server = new Server(model);
	         Naming.rebind("server", server);
	         System.out.println("Starting server...");
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	   }
}
