package server;

import java.io.Serializable;
import java.util.ArrayList;

import model.GroundNode;
import model.Plane;

public class Request implements Serializable
{

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum TYPE {
		REQUESTPLANES,REQUESTGROUNDNODES
	   }
	   
	   public TYPE type;
	   public ArrayList<Plane> planes;
	   public ArrayList<GroundNode> groundNodes;
	   
	   public Request(TYPE type)
	   {
		   this.type = type;
	   }
	   
	   @SuppressWarnings("unchecked")
	public <T> Request(TYPE type, ArrayList<T> list)
	   {
		   this.type = type;
		   if(type==TYPE.REQUESTPLANES)
		   {
			   this.planes=(ArrayList<Plane>) list;
		   }
		   if(type==TYPE.REQUESTGROUNDNODES)
		   {
			   this.groundNodes= (ArrayList<GroundNode>) list;
		   }
	   }
}
