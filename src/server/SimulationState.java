package server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Plane;

public class SimulationState implements Runnable {
	private Server server;
	private ArrayList<Plane> sendedPlanes;

	SimulationState(Server server) {
		this.server = server;
	}
	
	private void sendPlane()
	{
		
		for (int i = 0; i < server.getClients().size(); i++) {
			for (int j = 0; j < server.getModel().getPlanes().size(); j++) {
				try {
					server.sendPlane(server.getModel().getPlanes().get(j));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				server.getModel().addGroundPlane(server.getModel().getPlanes().get(j));
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void movePlanes() throws RemoteException
	{
		for (int i=0;i<server.getModel().getGroundPlanes().size();i++)
		{
			server.getModel().getGroundPlanes().get(i).movePlane();
		}
		for (int i=0;i<server.getClients().size();i++)
		{
			server.getGroundPlanes(server.getClients().get(i));
		}
	}
	
	private void checkCollision()
	{

	}

	@Override
	public void run() {
		while(true)
		{
		sendPlane();
		if( server.getModel().getGroundPlanes().size()>=1)
		{
		try {
			movePlanes();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		if(server.getModel().getGroundPlanes().size()>=2)
		{
		checkCollision();
		}
		}
	}
}
