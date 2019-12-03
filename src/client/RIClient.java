package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.GroundNodeDTO;
import model.Plane;
import model.PlaneDTO;

public interface RIClient extends Remote
{
	public void getPlaneDTOFromServer(PlaneDTO plane) throws RemoteException;
	
	public void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes) throws RemoteException;
	
	public void getGroundNodesDTOFromServer(ArrayList<GroundNodeDTO> nodes) throws RemoteException;
	
	public void simulationFailed() throws RemoteException;
}
