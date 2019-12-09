package airclientviewmodel;

import airclientmodel.ATCAirSimulator;

public class MainViewViewModel
{
   private ATCAirSimulator model;
   private AirRadarViewModel airRadarViewModel;
   
   public MainViewViewModel(ATCAirSimulator model)
   {
      this.model = model;
      this.airRadarViewModel = new AirRadarViewModel(model,model,model);
   }
   
   public AirRadarViewModel getAirRadarViewModel()
   {
      return airRadarViewModel;
   }

}
