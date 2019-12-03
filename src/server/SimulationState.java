package server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Plane;

public class SimulationState implements Runnable {
	private Server server;

	SimulationState(Server server) {
		this.server = server;
	}

	private void movePlanes() throws RemoteException {
		for (int i = 0; i < server.getModel().getGroundPlanes().size(); i++) {
			server.getModel().getGroundPlanes().get(i).movePlane();
		}
		for (int i = 0; i < server.getClients().size(); i++) {
			server.getGroundPlanesDTO(server.getClients().get(i));
		}
	}

	private boolean checkCollision() throws RemoteException {
		for (int i = 0; i < server.getModel().getGroundPlanes().size(); i++) {
			for (int j = i + 1; j < server.getModel().getGroundPlanes().size(); j++) {
				if (server.getModel().getGroundPlanes().get(i).getPosition()
						.equals(server.getModel().getGroundPlanes().get(j).getPosition())) {
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
			if (server.getModel().getGroundPlanes().size() >= 1) {
				try {
					movePlanes();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			if (server.getModel().getGroundPlanes().size() >= 2) {
				try {
					if (checkCollision()) {
						break;
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			try
         {
            Thread.sleep(1000);
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
		}
		for (int x = 0; x < server.getClients().size(); x++) {
			try {
				server.simulationFailed(server.getClients().get(x));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
