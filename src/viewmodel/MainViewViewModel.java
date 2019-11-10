package viewmodel;

import model.AirTrafficControlGroundSimulator;

public class MainViewViewModel
{
   
   private GroundRadarViewModel groundRadarViewModel;
   private PlaneViewModel planeViewModel;
   private AirTrafficControlGroundSimulator model;
   private GroundNodeViewModel groundNodeViewModel;
   
   public MainViewViewModel(AirTrafficControlGroundSimulator model)
   {
      this.model = model;
      this.groundRadarViewModel = new GroundRadarViewModel(model);
      this.planeViewModel = new PlaneViewModel();
      this.groundNodeViewModel = new GroundNodeViewModel(model);
      
   }
   
   public GroundRadarViewModel getGroundRadarViewModel()
   {
      return groundRadarViewModel;
   }
   
   public PlaneViewModel getPlaneViewModel()
   {
      return planeViewModel;
   }

}
