package model;

import java.util.ArrayList;

import groundClient.GroundClient;

public interface AirTrafficControlGroundSimulatorModelClientHandler 
{	
	void setClient(GroundClient groundClient);
	
	void getPlaneDTOFromServer(PlaneDTO plane);
	
	void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes);
	
	void getGroundNodesDTOFromServer(ArrayList<NodeDTO> nodes);
	
	void getWindFromServer(boolean wind);
	
	void simulationFailed();
	

}
