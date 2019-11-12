package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import model.Plane;
import server.Request;
import server.ServerModel;

public class ServerSocketHandler implements Runnable {
	private ServerModel model;
	private OutputStream out;
	private InputStream in;
	private Socket socket;

	public ServerSocketHandler(ServerModel model, Socket socket) {
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
		requestPlanes();
		while (true) {
			try {
				byte[] lenBytes = new byte[4];
		        in.read(lenBytes, 0, 4);
		        int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
		                  ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
		        byte[] receivedBytes = new byte[len];
		        in.read(receivedBytes, 0, len);
		        String received = new String(receivedBytes, 0, len);
		        Gson gson = new Gson();
		        Plane[] jsonString = gson.fromJson(received, Plane[].class );
System.out.println("data from server recieved");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void requestPlanes() {
		try
	      {
	         Request req = new Request(Request.TYPE.PLANESREQUEST);
	         Gson json = new Gson();
	         String objectToJson = json.toJson(req);
	         
	         byte[] toSendBytes = objectToJson.getBytes();
	         int toSendLen = toSendBytes.length;
	         byte[] toSendLenBytes = new byte[4];
	         toSendLenBytes[0] = (byte)(toSendLen & 0xff);
	         toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
	         toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
	         toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
	         out.write(toSendLenBytes);
	         out.write(toSendBytes);
	         System.out.println("A request has been sent");
	      }
	      catch (Exception e)
	      {

	         e.printStackTrace();
	      }
		
	}
	

}
