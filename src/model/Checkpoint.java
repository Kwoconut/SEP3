package model;

public class Checkpoint
{
   private String name;
   private Position position;
   private boolean isAirType;
   
   public Checkpoint(String name,Position position,boolean isAirType)
   {
      this.name = name;
      this.position = position;
      this.isAirType = isAirType;
   }

}
