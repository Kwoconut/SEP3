package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.ServerModel;

public class ServerSocketHandler implements Runnable {
	private ServerModel model;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;

	public ServerSocketHandler(ServerModel model, Socket socket) {
		this.model = model;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		/*while (true) {
			try {
				Request req = (Request) in.readObject();
				if (req.type == Request.TYPE.cevatipdescris) {
					model.cevametoda();
				} else if (req.type == Request.TYPE.alttipdescris) {
					model.cevametoda();
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}*/

	}

}
