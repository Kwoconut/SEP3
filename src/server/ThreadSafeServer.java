package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ThreadSafeServer implements ServerAccess, Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int readers;
   private int writers;
   private int waitingWriters;
   private Server server;

   public ThreadSafeServer(Server server) throws RemoteException
   {
      readers = 0;
      writers = 0;
      waitingWriters = 0;
      this.server = server;
      UnicastRemoteObject.exportObject(this, 0);
   }

   @Override
   public synchronized RIServerRead acquireRead() throws RemoteException
   {
      while (writers > 0 || waitingWriters > 0)
      {
         try
         {
            wait();
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      readers++;
      return server;
   }

   @Override
   public synchronized void releaseRead() throws RemoteException
   {
      readers--;
      if (readers == 0)
      {
         notify();
      }

   }

   @Override
   public synchronized RIServerWrite acquireWrite() throws RemoteException
   {
      waitingWriters++;
      while (readers > 0 || writers > 0)
      {
         try
         {
            wait();
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      waitingWriters--;
      writers++;
      return server;
   }

   @Override
   public synchronized void releaseWrite() throws RemoteException
   {
      writers--;
      notifyAll();

   }

}
