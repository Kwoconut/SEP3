package client;

import java.rmi.RemoteException;

public interface IClient
{

	void changePlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException;

}
