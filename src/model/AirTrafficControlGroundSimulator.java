package model;

import java.io.Serializable;
import java.util.ArrayList;

public class AirTrafficControlGroundSimulator implements AirTrafficControlGroundSimulatorModel, Serializable 
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Plane> planes;
	
	private ArrayList<GroundKeyLocation> gKLocations;

	@Override
	public void getPlanesFromServer(ArrayList<Plane> planes) 
	{
		this.planes = planes;
		System.out.println("Client got plane list from server");
	}

	@Override
	public void getGroundKLFromServer(ArrayList<GroundKeyLocation> gKLocations) 
	{
		this.gKLocations= gKLocations;
		System.out.println("Client got gKlocation list from server");
	}
}
