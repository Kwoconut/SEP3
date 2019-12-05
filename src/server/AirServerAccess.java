package server;

import java.rmi.RemoteException;

public interface AirServerAccess extends Releaser 
{
	AirRIServerRead acquireAirRead() throws RemoteException;
	
	AirRIServerWrite acquireAirWrite() throws RemoteException;
}
