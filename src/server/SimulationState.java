package server;

import java.rmi.RemoteException;

public class SimulationState implements Runnable {
	private SimulationManager manager;

	SimulationState(SimulationManager manager) {
		this.manager = manager;
	}

	private void movePlanes() throws RemoteException {
		for (int i = 0; i < manager.getServer().getModel().getGroundPlanes().size(); i++) {
			manager.getServer().getModel().getGroundPlanes().get(i).movePlane();
		}
		for (int i = 0; i < manager.getServer().getGroundClients().size(); i++) {
			manager.getServer().sendGroundPlanesDTO(manager.getServer().getGroundClients().get(i));
		}
		for (int i = 0; i < manager.getServer().getModel().getAirPlanes().size(); i++) {
			manager.getServer().getModel().getAirPlanes().get(i).movePlane();
		}
		for (int i = 0; i < manager.getServer().getAirClients().size(); i++) {
			manager.getServer().sendAirPlanesDTO(manager.getServer().getAirClients().get(i));
		}
	}
// to be changed
	private boolean checkCollision() throws RemoteException {
		for (int i = 0; i < manager.getServer().getModel().getGroundPlanes().size(); i++) {
			for (int j = i + 1; j < manager.getServer().getModel().getGroundPlanes().size(); j++) {
				if (manager.getServer().getModel().getGroundPlanes().get(i).getPosition()
						.equals(manager.getServer().getModel().getGroundPlanes().get(j).getPosition())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		System.out.println("SimulationState started");
		while (true) {
			if (manager.getServer().getModel().getGroundPlanes().size() >= 1) {
				try {
					movePlanes();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			if (manager.getServer().getModel().getGroundPlanes().size() >= 2) {
				try {
					if (checkCollision()) {
						manager.exitPlaneDispatcher();
						System.out.println("Simulation State Thread Stopped");
						break;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			try
         {
            Thread.sleep(0100);
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
		}
		for (int x = 0; x < manager.getServer().getGroundClients().size(); x++) {
			try {
				manager.getServer().simulationFailed(manager.getServer().getGroundClients().get(x));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		for (int x = 0; x < manager.getServer().getAirClients().size(); x++) {
			try {
				manager.getServer().airSimulationFailed(manager.getServer().getAirClients().get(x));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
