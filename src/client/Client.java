package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.AirTrafficControlGroundSimulator;
import model.AirTrafficControlGroundSimulatorModel;
import model.AirTrafficControlGroundSimulatorModelClientHandler;
import model.GroundKeyLocation;
import model.Plane;
import server.RemoteServer;

public class Client implements RIClient, Serializable 
{
	private static final long serialVersionUID = 1L;
	private AirTrafficControlGroundSimulatorModelClientHandler model;
	private RemoteServer server;

	public Client(AirTrafficControlGroundSimulatorModelClientHandler model)
			throws RemoteException, NotBoundException, MalformedURLException {
		this.model = model;
		server = (RemoteServer) Naming.lookup("rmi://localhost:1099/server");
		server.addClient(this);
	}

	@Override
	public void getPlanesFromServer(ArrayList<Plane> planes) {
		model.getPlanesFromServer(planes);
	}

	@Override
	public void getGrundKLFromServer(ArrayList<GroundKeyLocation> gKLocations) {
		model.getGroundKLFromServer(gKLocations);
	}
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException 
	{
		AirTrafficControlGroundSimulatorModel model = new AirTrafficControlGroundSimulator();
		Client client = new Client(model);
	}

}
