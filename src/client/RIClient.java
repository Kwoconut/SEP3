package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Plane;

public interface RIClient extends Remote
{
	public void getPlaneFromServer(Plane plane) throws RemoteException;
	
	public void getGroundPlanesFromServer(ArrayList<Plane> planes) throws RemoteException;
	
	public void simulationFailed() throws RemoteException;
}
