package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.RIClient;

public interface RIServerRead extends Remote
{

	void getGroundPlanes(RIClient client) throws RemoteException;

}
