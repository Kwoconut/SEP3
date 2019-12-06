import groundClientModel.AirTrafficControlGroundSimulator;
import groundClientModel.AirTrafficControlGroundSimulatorModel;
import groundclient.GroundClient;
import groundclient.GroundRIClient;
import groundclientview.MainView;
import groundclientviewmodel.MainViewViewModel;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Plane;
import model.StaticPosition;

public class GroundClientSetUp extends Application
{
   @Override
   public void start(Stage stage) throws Exception
   {
      AirTrafficControlGroundSimulator model = new AirTrafficControlGroundSimulatorModel();
      MainViewViewModel mvvm = new MainViewViewModel(model);
      GroundRIClient client = new GroundClient(model);
      MainView mv = new MainView(stage, mvvm);
      mv.start();
/*      TestTimerRunnable timer = new TestTimerRunnable(model);
      Thread thread = new Thread(timer);
      thread.start();*/
      


   }

}
