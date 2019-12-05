package model;

import java.util.ArrayList;

import client.Client;

public interface AirTrafficControlGroundSimulatorModelClientHandler 
{	
	void getPlaneDTOFromServer(PlaneDTO plane);
	
	void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes);
	
	void setClient(Client client);
	
	void simulationFailed();
	
	void getGroundNodesDTOFromServer(ArrayList<GroundNodeDTO> nodes);
	
	void getWindFromServer(boolean wind);

}
