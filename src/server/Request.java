package server;

import java.io.Serializable;

public class Request implements Serializable
{
	private static final long serialVersionUID = -8955658543354406466L;

	   public enum TYPE {
	      PLANESREQUEST,
	   }
	   
	   public TYPE type;
	   
	   public Request(TYPE type)
	   {
		   this.type = type;
	   }
}
