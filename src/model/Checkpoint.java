package model;

import java.io.Serializable;

public class Checkpoint implements Serializable
{
   private String name;
   private Position position;
   
   public Checkpoint(String name,Position position,boolean isAirType)
   {
      this.name = name;
      this.position = position;
   }

}
