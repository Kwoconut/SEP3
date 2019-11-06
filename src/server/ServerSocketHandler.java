package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import com.google.gson.Gson;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import server.Request;
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
		requestPlanes();

		/*while (true) {
			try {
				Gson gson = (Gson) in.readObject();
				Request req = new Request(gson.TYPE)  ceva de genu aici tre de vazut
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

	private void requestPlanes() {
		try
	      {
	         Request req = new Request(Request.TYPE.PLANESREQUEST);
	         Gson json = new Gson();
	         json.toJson(req);
	         out.writeObject(json);
	      }
	      catch (IOException e)
	      {

	         e.printStackTrace();
	      }
		
	}

}
