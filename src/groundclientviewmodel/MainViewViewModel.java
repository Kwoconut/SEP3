package groundclientviewmodel;

import groundclientmodel.AirTrafficControlGroundSimulator;

public class MainViewViewModel
{

   private AirTrafficControlGroundSimulator model;
   private GroundRadarViewModel groundRadarViewModel;

   public MainViewViewModel(AirTrafficControlGroundSimulator model)
   {
      this.model = model;
      this.groundRadarViewModel = new GroundRadarViewModel(model, model, model);
   }

   public GroundRadarViewModel getGroundRadarViewModel()
   {
      return groundRadarViewModel;
   }
   

}
