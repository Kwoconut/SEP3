package server;

import java.rmi.RemoteException;

import client.RIClient;

public interface RIServerWrite extends RIServerRead 
{
	void addClient(RIClient client) throws RemoteException;
}
