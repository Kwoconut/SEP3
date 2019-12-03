package server;

import java.rmi.RemoteException;

import model.PlaneDTO;

public class PlaneDispatcher implements Runnable {

	private SimulationManager manager;

	PlaneDispatcher(SimulationManager manager) {
		this.manager = manager;
	}

	private void sendPlane() throws InterruptedException {

		for (int i = 0; i < manager.getServer().getModel().getPlanes().size(); i++) {
			if(!manager.getExitPlaneDispatcher())
			{
			for (int j = 0; j < manager.getServer().getClients().size(); j++) {
				try {
					manager.getServer().getModel().getPlanes().get(i).landPlane();
					manager.getServer().sendPlaneDTO(manager.getServer().getModel().getPlanes().get(i).convertToDTO(),manager.getServer().getClients().get(j));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			manager.getServer().getModel().addGroundPlane(manager.getServer().getModel().getPlanes().get(i));
			Thread.sleep(10000);
		}
			else
			{
			System.out.println("Plane Dispatcher Thread Stopped");
			break;
			}
		}
	}
	
	@Override
	public void run() {
		{
			{
			System.out.println("PlaneDispatcher Started");
			try {
				sendPlane();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
	
}