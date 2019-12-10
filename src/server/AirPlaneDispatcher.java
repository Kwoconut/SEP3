package server;

import java.rmi.RemoteException;

public class AirPlaneDispatcher implements Runnable {

	private SimulationManager manager;

	AirPlaneDispatcher(SimulationManager manager) {
		this.manager = manager;
	}

	private void sendAirPlane() {

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < manager.getServer().getAirClients().size(); j++) {
				try {
					manager.getServer().sendAirPlaneDTO(
							manager.getServer().getModel().getAirPlanes().get(i).convertToDTO(),
							manager.getServer().getAirClients().get(j));
				//	manager.getServer().getModel().getGroundPlanes().add(manager.getServer().getModel().getAirPlanes().get(i));
				//	manager.getServer().getModel().getAirPlanes().remove(i);
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
	public void run() {
		System.out.println("GroundPlaneDispatcher Started");
		sendAirPlane();

	}

}
