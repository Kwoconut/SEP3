package server;

import java.io.Serializable;

public class Request implements Serializable
{

	   public enum TYPE {
	      PLANESREQUEST,
	   }
	   
	   public TYPE type;
	   
	   public Request(TYPE type)
	   {
		   this.type = type;
	   }
}
