package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.RIClient;

public interface RemoteServer extends Remote 
{
	public void addClient(RIClient client) throws RemoteException;
}
