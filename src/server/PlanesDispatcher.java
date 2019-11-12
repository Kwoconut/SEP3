package server;

import java.rmi.RemoteException;

import client.RIClient;
import model.Plane;

public class PlanesDispatcher implements Runnable
{
	private Server server;
	
	PlanesDispatcher()
	{	
	}

	@Override
	public void run() {
		for(int i=0;i<server.getModel().getPlanes().size();i++)
		{
			try {
				server.sendPlane(server.getModel().getPlanes().get(i));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
        try
        {
           Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
           e.printStackTrace();
        }
	}
}
