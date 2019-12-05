package server;

import java.rmi.RemoteException;

public interface GroundServerAccess extends Releaser
{
	GroundRIServerRead acquireGroundRead() throws RemoteException;
	
	GroundRIServerWrite acquireGroundWrite()throws RemoteException; 
}
