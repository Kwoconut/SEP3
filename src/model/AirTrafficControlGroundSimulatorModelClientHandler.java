package model;

import java.util.ArrayList;

import client.Client;

public interface AirTrafficControlGroundSimulatorModelClientHandler 
{	
	void getPlaneFromServer(Plane plane);
	
	void setClient(Client client);
}
