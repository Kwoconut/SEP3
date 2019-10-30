package model;

public class GroundKeyLocation
{
   private String name;
   
   private Checkpoint checkpoint;
   
   private String type;
   
   public GroundKeyLocation(String name,Checkpoint checkpoint,String type)
   {
      this.name = name;
      this.checkpoint = checkpoint;
      this.type = type;
   }

}
