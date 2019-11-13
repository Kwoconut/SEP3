package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerAccess extends Remote 
{
	RIServerRead acquireRead() throws RemoteException;
	
	void releaseRead() throws RemoteException;
	
	RIServerWrite acquireWrite()throws RemoteException; 
	
	void releaseWrite()throws RemoteException;

}
