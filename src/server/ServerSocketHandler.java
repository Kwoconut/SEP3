package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import model.Edge;
import model.GroundNode;
import model.Plane;
import server.ServerModel;

public class ServerSocketHandler implements Runnable
{
   private ServerModel model;
   private OutputStream out;
   private InputStream in;
   private Socket socket;

   public ServerSocketHandler(ServerModel model, Socket socket)
   {
      this.model = model;
      this.socket = socket;
      try
      {
         in = socket.getInputStream();
         out = socket.getOutputStream();
      }
      catch (IOException e)
      {

         e.printStackTrace();
      }
   }

   @Override
   public void run()
   {
      {
         try
         {
            byte[] lenBytes = new byte[4];
            in.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24)
                  | ((lenBytes[2] & 0xff) << 16) | ((lenBytes[1] & 0xff) << 8)
                  | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            in.read(receivedBytes, 0, len);
            String received = new String(receivedBytes, 0, len);
            Gson gson = new Gson();
            Plane[] jsonString = gson.fromJson(received, Plane[].class);
            ArrayList<Plane> planes = new ArrayList<Plane>(
                  Arrays.asList(jsonString));
            this.model.loadPlanesFromDatabase(planes);

            byte[] lenBytes1 = new byte[4];
            in.read(lenBytes1, 0, 4);
            int len1 = (((lenBytes1[3] & 0xff) << 24)
                  | ((lenBytes1[2] & 0xff) << 16) | ((lenBytes1[1] & 0xff) << 8)
                  | (lenBytes1[0] & 0xff));
            byte[] receivedBytes1 = new byte[len1];
            in.read(receivedBytes1, 0, len1);
            String received1 = new String(receivedBytes1, 0, len1);
            GroundNode[] jsonString1 = gson.fromJson(received1,
                  GroundNode[].class);
            ArrayList<GroundNode> groundNodes = new ArrayList<GroundNode>(
                  Arrays.asList(jsonString1));
            this.model.loadGroundNodesFromDatabase(groundNodes);
            
            byte[] lenBytes2 = new byte[4];
            in.read(lenBytes2, 0, 4);
            int len2 = (((lenBytes2[3] & 0xff) << 24)
                  | ((lenBytes2[2] & 0xff) << 16) | ((lenBytes2[1] & 0xff) << 8)
                  | (lenBytes2[0] & 0xff));
            byte[] receivedBytes2 = new byte[len2];
            in.read(receivedBytes2, 0, len2);
            String received2 = new String(receivedBytes2, 0, len2);
            Edge[] jsonString2 = gson.fromJson(received2,
                  Edge[].class);
            ArrayList<Edge> edges = new ArrayList<Edge>(
                  Arrays.asList(jsonString2));
            this.model.loadEdgesFromDatabase(edges);
            
            socket.close();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }
}
