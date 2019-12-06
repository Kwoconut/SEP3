package airClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.NodeDTO;
import model.PlaneDTO;
import model.Timer;

public interface AirRIClient 
{
	public void getAirPlaneDTOFromServer(PlaneDTO plane) throws RemoteException;
	
	public void getAirPlanesDTOFromServer(ArrayList<PlaneDTO> planes) throws RemoteException;
	
	public void getAirNodesDTOFromServer(ArrayList<NodeDTO> nodes) throws RemoteException;
		
	public void getWindFromServer(boolean wind) throws RemoteException;

	public void simulationFailed() throws RemoteException;

   public void getTimerFromServer(Timer timer) throws RemoteException;
}
