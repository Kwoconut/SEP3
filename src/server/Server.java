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
		sendPlanes(client);
	}
	
	public void sendPlanes(RIClient sender) throws RemoteException
	{
		sender.getPlanesFromServer(model.getPlanes());
	}
	
	public void execute() throws IOException
	{
		System.out.println("Starting socket part");
		while(true)
		{
			System.out.println("Waiting for clients ...");
			Socket socket = new Socket("10.152.202.140", 200);
			Thread t = new Thread(new ServerSocketHandler(model,socket));
			t.start();
		}
	}
	public static void main(String[] args) throws IOException {
		try {
			LocateRegistry.createRegistry(1099);
			ServerModel model = new ServerModel();
			RemoteServer server = new Server(model);
			Naming.rebind("server", server);
			System.out.println("Starting RMI part");
			server.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
