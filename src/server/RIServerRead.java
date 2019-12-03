package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.RIClient;

public interface RIServerRead extends Remote
{

	void getGroundPlanesDTO(RIClient client) throws RemoteException;
	
	void getGroundNodesDTO(RIClient client) throws RemoteException;

}
