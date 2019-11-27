package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import model.Plane;
import server.Request;
import server.ServerModel;

public class ServerSocketHandler implements Runnable {
	private ServerModel model;
	private OutputStream out;
	private InputStream in;
	private Socket socket;

	public ServerSocketHandler(ServerModel model, Socket socket) {
		System.out.println("ss");
		this.model = model;
		this.socket = socket;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("1");
		requestPlanes();
		System.out.println("2");
		requestGroundNodes();
		while (true) {
			try {
				byte[] lenBytes = new byte[4];
				in.read(lenBytes, 0, 4);
				int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) | ((lenBytes[1] & 0xff) << 8)
						| (lenBytes[0] & 0xff));
				byte[] receivedBytes = new byte[len];
				in.read(receivedBytes, 0, len);
				String received = new String(receivedBytes, 0, len);
				System.out.println(received);
				System.out.println("data from server recieved");
				Gson gson = new Gson();
				Request jsonString = gson.fromJson(received, Request.class);
				if (jsonString.type == Request.TYPE.REQUESTPLANES) {
					this.model.loadPlanesFromDatabase(jsonString.planes);
					System.out.println("data from server about planes sent to server model");
				}
				if (jsonString.type == Request.TYPE.REQUESTGROUNDNODES) {
					this.model.loadGroundNodesFromDatabase(jsonString.groundNodes);
					System.out.println("data from server about ground nodes sent to server model");
				} else {
					System.out.println("Wrong Request Type");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void requestPlanes() {
		try {
			Request req = new Request(Request.TYPE.REQUESTPLANES);
			Gson json = new Gson();
			String objectToJson = json.toJson(req);

			byte[] toSendBytes = objectToJson.getBytes();
			int toSendLen = toSendBytes.length;
			byte[] toSendLenBytes = new byte[4];
			toSendLenBytes[0] = (byte) (toSendLen & 0xff);
			toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
			toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
			toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
			out.write(toSendLenBytes);
			out.write(toSendBytes);
			System.out.println("A request for planes has been sent");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void requestGroundNodes() {
		try {
			Request req = new Request(Request.TYPE.REQUESTGROUNDNODES);
			Gson json = new Gson();
			String objectToJson = json.toJson(req);

			byte[] toSendBytes = objectToJson.getBytes();
			int toSendLen = toSendBytes.length;
			byte[] toSendLenBytes = new byte[4];
			toSendLenBytes[0] = (byte) (toSendLen & 0xff);
			toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
			toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
			toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
			out.write(toSendLenBytes);
			out.write(toSendBytes);
			System.out.println("A request for ground nodes has been sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
