package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.AirTrafficControlGroundSimulatorModelClientHandler;
import model.Plane;
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
		serverRead.getGroundPlanes(this);
		access.releaseRead();
	}

	@Override
	public void getPlaneFromServer(Plane plane) throws RemoteException {
		model.getPlaneFromServer(plane);
	}

	@Override
	public void getGroundPlanesFromServer(ArrayList<Plane> planes) throws RemoteException {
		model.getGroundPlanesFromServer(planes);
	}

	@Override
	public void simulationFailed() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
