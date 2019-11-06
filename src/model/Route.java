package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable
{
   private String startLocation;
   private String destination;
   private ArrayList<Checkpoint> checkpoints;

   public Route(String startLocation, String destination,
         ArrayList<Checkpoint> checkpoints)
   {

   this.startLocation = startLocation;
   this.destination = destination;
   this.checkpoints = checkpoints;
   }

}
