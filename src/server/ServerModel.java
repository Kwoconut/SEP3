package server;

import java.util.ArrayList;
import java.util.Date;

import model.Checkpoint;
import model.FlightPlan;
import model.Plane;
import model.Position;
import model.Route;

public class ServerModel 
{	
	private ArrayList<Plane> planes;
	
	public ServerModel()
	{
		planes = new ArrayList<Plane>();
		loadPlanesFromDatabase();
	}
	//scoatem din database , tre de modificat la finisarea databasului
	@SuppressWarnings("deprecation")
	public void loadPlanesFromDatabase() 
	{
		Position position1 = new Position(100,200);
		Position position2 = new Position(200,300);
		Checkpoint checkpoint1 = new Checkpoint("checkpoint1",position1,false);
		Checkpoint checkpoint2 = new Checkpoint("checkpoint2",position2,false);
		ArrayList<Checkpoint> checkpoints1= new ArrayList<Checkpoint>();
		checkpoints1.add(checkpoint1);
		checkpoints1.add(checkpoint2);
		Route route1 = new Route("position1", "position2", checkpoints1);
		Date date1 = new Date(1997,12,29);
		Date date2 = new Date(1997,12,30);
		Date date3 = new Date(1997,12,31);
		FlightPlan flightPlan1 = new FlightPlan("id1", date1, date2,
		         date3, route1);
		Plane plane1 = new Plane("Eagle1", "model1", "company1", "status1",
		          flightPlan1, position1);
		planes.add(plane1);
		System.out.println("Server loaded planes from db");
	}
	
	public ArrayList<Plane> getPlanes()
	{
		return planes;
	}	
	
}
