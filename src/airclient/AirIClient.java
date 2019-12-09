package airclient;

import java.rmi.RemoteException;

import model.StaticPosition;

public interface AirIClient 
{
   void reRoutePlane(String callSign,StaticPosition position);
   
}
