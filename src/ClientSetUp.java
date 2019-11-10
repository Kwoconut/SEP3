import client.Client;
import client.RIClient;
import javafx.application.Application;
import javafx.stage.Stage;
import model.AirTrafficControlGroundSimulator;
import model.AirTrafficControlGroundSimulatorModel;
import test.TestTimerRunnable;
import view.MainView;
import viewmodel.MainViewViewModel;

public class ClientSetUp extends Application
{
   @Override
   public void start(Stage stage) throws Exception
   {
      AirTrafficControlGroundSimulator model = new AirTrafficControlGroundSimulatorModel();
      MainViewViewModel mvvm = new MainViewViewModel(model);
      MainView view = new MainView(stage, mvvm);
      TestTimerRunnable timer = new TestTimerRunnable(model);
      Thread thread = new Thread(timer);
      view.start();
      thread.start();
   }

}
