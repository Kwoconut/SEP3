package groundclient;

import java.rmi.RemoteException;

public interface GroundIClient
{
	void changeGroundPlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException;
}
