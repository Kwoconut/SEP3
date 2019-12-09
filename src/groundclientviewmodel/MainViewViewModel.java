package groundclientviewmodel;

import groundclientmodel.ATCGroundSimulator;

public class MainViewViewModel
{

   private ATCGroundSimulator model;
   private GroundRadarViewModel groundRadarViewModel;

   public MainViewViewModel(ATCGroundSimulator model)
   {
      this.model = model;
      this.groundRadarViewModel = new GroundRadarViewModel(model, model, model);
   }

   public GroundRadarViewModel getGroundRadarViewModel()
   {
      return groundRadarViewModel;
   }
   

}
