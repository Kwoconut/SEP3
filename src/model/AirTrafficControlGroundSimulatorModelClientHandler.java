package model;

import java.util.ArrayList;

import client.Client;

public interface AirTrafficControlGroundSimulatorModelClientHandler 
{	
	void getPlanesFromServer(ArrayList<Plane> planes);
	
	void setClient(Client client);
}
