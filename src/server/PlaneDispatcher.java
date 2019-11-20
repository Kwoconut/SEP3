package server;

import java.rmi.RemoteException;

public class PlaneDispatcher implements Runnable {

	private Server server;

	PlaneDispatcher(Server server) {
		this.server = server;
	}

	private void sendPlane() {

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
		while(true)
		{
			sendPlane();
		}
		
	}
}