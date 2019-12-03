package server;

import java.rmi.RemoteException;

import model.PlaneDTO;

public class PlaneDispatcher implements Runnable {

	private SimulationManager manager;

	PlaneDispatcher(SimulationManager manager) {
		this.manager = manager;
	}

	private void sendPlane() {

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < manager.getServer().getClients().size(); j++) {
				try {
					manager.getServer().getModel().getPlanes().get(i).landPlane();
					manager.getServer().sendPlaneDTO(manager.getServer().getModel().getPlanes().get(i).convertToDTO(),manager.getServer().getClients().get(j));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			manager.getServer().getModel().addGroundPlane(manager.getServer().getModel().getPlanes().get(i));
			try {
				//
				//
				//
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		{
			System.out.println("PlaneDispatcher Started");
			sendPlane();
		}
		
	}
}