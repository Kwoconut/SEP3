package server;

import java.rmi.RemoteException;

public class GroundPlaneDispatcher implements Runnable {

	private SimulationManager manager;

	GroundPlaneDispatcher(SimulationManager manager) {
		this.manager = manager;
	}

	private void sendGroundPlane() {

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < manager.getServer().getGroundClients().size(); j++) {
				try {
					manager.getServer().sendGroundPlaneDTO(
							manager.getServer().getModel().getGroundPlanes().get(i).convertToDTO(),
							manager.getServer().getGroundClients().get(j));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	public void run()
	{
		System.out.println("AirPlaneDispatcher Started");
		sendGroundPlane();
	}
}