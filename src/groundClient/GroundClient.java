package groundClient;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.AirTrafficControlGroundSimulatorModelClientHandler;
import model.NodeDTO;
import model.Plane;
import model.PlaneDTO;
import server.GroundRIServerRead;
import server.GroundRIServerWrite;
import server.GroundServerAccess;

public class GroundClient implements GroundRIClient, GroundIClient, Serializable {
	private static final long serialVersionUID = 1L;
	private AirTrafficControlGroundSimulatorModelClientHandler model;
	private GroundServerAccess access;

	public GroundClient(AirTrafficControlGroundSimulatorModelClientHandler model)
			throws RemoteException, NotBoundException, MalformedURLException {
		this.model = model;
		this.model.setClient(this);
		access = (GroundServerAccess) Naming.lookup("rmi://localhost:1099/server");
		UnicastRemoteObject.exportObject(this, 0);
		GroundRIServerWrite server = access.acquireGroundWrite();
		server.addGroundClient(this);
		access.releaseWrite();
		GroundRIServerRead serverRead = access.acquireGroundRead();
		serverRead.sendGroundNodesDTO(this);
		serverRead.sendGroundPlanesDTO(this);
		serverRead.sendGroundWind(this);
		access.releaseRead();
	}

	@Override
	public void getGroundPlaneDTOFromServer(PlaneDTO plane) throws RemoteException {
		model.getPlaneDTOFromServer(plane);
	}

	@Override
	public void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes) throws RemoteException {
		model.getGroundPlanesDTOFromServer(planes);
	}

   @Override
   public void getGroundNodesDTOFromServer(ArrayList<NodeDTO> nodes)
         throws RemoteException
   {
      model.getGroundNodesDTOFromServer(nodes);
      
   }

   @Override
   public void getWindFromServer(boolean wind) throws RemoteException
   {
      model.getWindFromServer(wind);
 
   }

   @Override
   public void simulationFailed() throws RemoteException {
	   model.simulationFailed();
   }
   
   @Override
   public void changeGroundPlaneRoute(String callSign, int startNodeId, int endNodeId) throws RemoteException {
	   GroundRIServerWrite server = access.acquireGroundWrite();
	   server.changeGroundPlaneRoute(callSign,startNodeId,endNodeId);
	   access.releaseWrite();  
   }
}
