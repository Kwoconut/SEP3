package server;

import java.util.ArrayList;
import java.util.Date;

import model.Checkpoint;
import model.FlightPlan;
import model.Plane;
import model.Position;

public class ServerModel 
{	
	private ArrayList<Plane> planes;
	
	public ServerModel()
	{
		planes = new ArrayList<Plane>();
	}
	public void loadPlanesFromDatabase(ArrayList<Plane> planes) 
	{
		this.planes = planes;
	}
	
	public ArrayList<Plane> getPlanes()
	{
		return planes;
	}	
	
}
