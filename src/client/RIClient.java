package client;

import java.rmi.Remote;
import java.util.ArrayList;

import model.GroundKeyLocation;
import model.Plane;

public interface RIClient extends Remote
{
	public void getPlanesFromServer(ArrayList<Plane> planes);
	
	public void getGrundKLFromServer(ArrayList<GroundKeyLocation> gKLocations);
}
