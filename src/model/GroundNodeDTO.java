package model;

import java.io.Serializable;

public class GroundNodeDTO implements Serializable
{
   private int nodeId;
   private StaticPosition position;
   
   public GroundNodeDTO(int nodeId, StaticPosition position)
   {
      this.nodeId = nodeId;
      this.position = position;
   }
   
   public int getNodeId()
   {
      return nodeId;
   }
   
   public StaticPosition getPosition()
   {
      return position;
   }

}
