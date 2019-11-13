package server;

import java.util.ArrayList;
import model.Plane;

public class ServerModel 
{	
	private ArrayList<Plane> planes;
	private ArrayList<Plane> groundPlanes;
	
	public ServerModel()
	{
		planes = new ArrayList<Plane>();
		groundPlanes = new ArrayList<Plane>();
	}
	public void loadPlanesFromDatabase(ArrayList<Plane> planes) 
	{
		this.planes = planes;
	}
	
	public ArrayList<Plane> getPlanes()
	{
		return planes;
	}	
	
	public ArrayList<Plane> getGroundPlanes()
	{
		if (groundPlanes.size() ==-1)
		{
		}
		return groundPlanes;
	}
	public void addGroundPlane(Plane plane)
	{
		groundPlanes.add(plane);
	}
	
}
