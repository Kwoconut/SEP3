package groundClientModel;

import java.util.ArrayList;

import groundclient.GroundClient;
import model.NodeDTO;
import model.PlaneDTO;
import model.Timer;

public interface AirTrafficControlGroundSimulatorModelClientHandler 
{	
	void setClient(GroundClient groundClient);
	
	void getPlaneDTOFromServer(PlaneDTO plane);
	
	void getGroundPlanesDTOFromServer(ArrayList<PlaneDTO> planes);
	
	void getGroundNodesDTOFromServer(ArrayList<NodeDTO> nodes);
	
	void getWindFromServer(boolean wind);
	
	void simulationFailed();

   void getTimerFromServer(Timer timer);
	
}
