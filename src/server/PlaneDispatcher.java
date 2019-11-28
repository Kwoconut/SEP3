package server;

import java.rmi.RemoteException;

import model.PlaneDTO;

public class PlaneDispatcher implements Runnable {

	private Server server;

	PlaneDispatcher(Server server) {
		this.server = server;
	}

	private void sendPlane() {

		for (int i = 0; i < server.getModel().getPlanes().size(); i++) {
			for (int j = 0; j < server.getClients().size(); j++) {
				try {
					server.getModel().getPlanes().get(i).landPlane();
					server.sendPlaneDTO(server.getModel().getPlanes().get(i).convertToDTO(),server.getClients().get(j));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			server.getModel().addGroundPlane(server.getModel().getPlanes().get(i));
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