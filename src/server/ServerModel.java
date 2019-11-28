package server;

import java.util.ArrayList;

import model.AirportGraph;
import model.GroundNode;
import model.InAirState;
import model.LandedState;
import model.LandingState;
import model.Plane;
import model.PlaneDTO;
import model.StaticPosition;

public class ServerModel {
	private ArrayList<Plane> planes;
	private ArrayList<Plane> groundPlanes;
	private ArrayList<GroundNode> groundNodes;
	private AirportGraph airportGraph;

	public ServerModel() {
		planes = new ArrayList<Plane>();
		groundPlanes = new ArrayList<Plane>();
	}

	public void loadPlanesFromDatabase(ArrayList<Plane> planes) {
		for(int i=0;i<planes.size();i++)
		{
			planes.get(i).setState(new InAirState());
			
		}
		this.planes = planes;
	}

	public void loadGroundNodesFromDatabase(ArrayList<GroundNode> groundNodes) {
		this.groundNodes = groundNodes;
		airportGraph = new AirportGraph(groundNodes);

	}

	public ArrayList<Plane> getPlanes() {
		return planes;
	}

	public ArrayList<Plane> getGroundPlanes() {
		if (groundPlanes.size() == -1) {
		}
		return groundPlanes;
	}

	public ArrayList<PlaneDTO> getGroundPlanesDTO() {
		if (groundPlanes.size() == -1) {
		}
		ArrayList<PlaneDTO> planesToSend = new ArrayList<PlaneDTO>();
		for (int i = 0; i < groundPlanes.size(); i++) {
			planesToSend.add(groundPlanes.get(i).convertToDTO());
		}
		return planesToSend;
	}

	public void addGroundPlane(Plane plane) {
		groundPlanes.add(plane);
	}

	public void changePlaneRoute(String callSign, int startNodeId, int endNodeId) {
		int startNode = 0;
		int endNode = 0;
		int plane = 0;

		for (int i = 0; i < groundNodes.size(); i++) {
			if (groundNodes.get(i).getNodeId() == startNodeId) {
				startNode = i;
				break;
			}
		}

		for (int i = 0; i < groundPlanes.size(); i++) {
			if (groundPlanes.get(i).getCallSign().equals(callSign)) {
				plane = i;
				break;
			}
		}

		for (int i = 0; i < groundNodes.size(); i++) {
			if (groundNodes.get(i).getNodeId() == endNodeId) {
				endNode = i;
				break;
			}
		}

		ArrayList<GroundNode> shortestDistance = airportGraph.calculateShortestDistance(groundNodes.get(startNode),
				groundNodes.get(endNode));
		ArrayList<StaticPosition> route = new ArrayList<StaticPosition>();
		for (int i = 0; i < shortestDistance.size(); i++) {
			route.add(shortestDistance.get(i).getPosition());
		}
		groundPlanes.get(plane).setRoute(route);
	}

}
