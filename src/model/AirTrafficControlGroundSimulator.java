package model;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;
import client.IClient;

public class AirTrafficControlGroundSimulator implements AirTrafficControlGroundSimulatorModel, Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Plane> planes;
	private IClient client;

	@Override
	public void getPlanesFromServer(ArrayList<Plane> planes) {
		this.planes = planes;
		System.out.println("Client got plane list from server");
	}

	@Override
	public void setClient(Client client) {
		this.client = client;

	}
}
