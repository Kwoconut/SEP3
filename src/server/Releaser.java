package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Releaser extends Remote 
{
	public void releaseRead() throws RemoteException;
	
	public void releaseWrite() throws RemoteException;
}
