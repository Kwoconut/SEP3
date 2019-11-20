import client.Client;
import client.RIClient;
import javafx.application.Application;
import javafx.stage.Stage;
import model.AirTrafficControlGroundSimulator;
import model.AirTrafficControlGroundSimulatorModel;
import model.Plane;
import model.StaticPosition;
import view.MainView;
import viewmodel.MainViewViewModel;

public class ClientSetUp extends Application
{
   @Override
   public void start(Stage stage) throws Exception
   {
      AirTrafficControlGroundSimulator model = new AirTrafficControlGroundSimulatorModel();
 //     RIClient client = new Client(model);
      MainViewViewModel mvvm = new MainViewViewModel(model);
      MainView mv = new MainView(stage, mvvm);
      mv.start();
      TestTimerRunnable timer = new TestTimerRunnable(model);
      Thread thread = new Thread(timer);
      thread.start();
      


   }

}
