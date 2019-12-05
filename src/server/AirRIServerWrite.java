package server;

import java.rmi.RemoteException;
import airClient.AirRIClient;

public interface AirRIServerWrite extends AirRIServerRead
{
	void addAirClient(AirRIClient client) throws RemoteException;

	void changeAirPlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException;
}
