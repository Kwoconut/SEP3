package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.AirTrafficControlGroundSimulatorModelClientHandler;
import model.GroundNodeDTO;
import model.Plane;
import model.PlaneDTO;
import server.RIServerRead;
import server.RIServerWrite;
import server.ServerAccess;

public class Client implements RIClient, IClient, Serializable {
	private static final long serialVersionUID = 1L;
	private AirTrafficControlGroundSimulatorModelClientHandler model;
	private ServerAccess access;

	public Client(AirTrafficControlGroundSimulatorModelClientHandler model)
			throws RemoteException, NotBoundException, MalformedURLException {
		this.model = model;
		this.model.setClient(this);
		access = (ServerAccess) Naming.lookup("rmi://localhost:1099/server");
		UnicastRemoteObject.exportObject(this, 0);
		RIServerWrite server = access.acquireWrite();
		server.addClient(this);
		access.releaseWrite();
		RIServerRead serverRead = access.acquireRead();
		serverRead.getGroundNodesDTO(this);
		serverRead.getGroundPlanesDTO(this);
		serverRead.getWind(this);
		access.releaseRead();
	}

	@Override
	public void getPlaneDTOFromServer(PlaneDTO plane) throws RemoteException {
		model.getPlaneDTOFromServer(plane);
	}

	@Override
	public void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes) throws RemoteException {
		model.getGroundPlanesDTOFromServer(planes);
	}

	@Override
	public void simulationFailed() throws RemoteException {
		model.simulationFailed();
	}

	@Override
	public void changePlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException {
		RIServerWrite server = access.acquireWrite();
		server.changePlaneRoute(callSign,startNodeId,endNodeId);
		access.releaseWrite();
		
	}

   @Override
   public void getGroundNodesDTOFromServer(ArrayList<GroundNodeDTO> nodes)
         throws RemoteException
   {
      model.getGroundNodesDTOFromServer(nodes);
      
   }

   @Override
   public void getWindFromServer(boolean wind) throws RemoteException
   {
      model.getWindFromServer(wind);
 
   }

}
