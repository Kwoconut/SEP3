package airClient;

import java.rmi.RemoteException;

public interface AirIClient 
{
	void changeAirPlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException;
}
