package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewmodel.MainViewViewModel;

public class MainView
{
   private Stage stage;
   private MainViewViewModel mvViewModel;

   public MainView(Stage stage, MainViewViewModel mvViewModel)
   {
      this.stage = stage;
      this.mvViewModel = mvViewModel;
   }

   public void start() throws Exception
   {
      openView("GroundRadar");
   }

   public void openView(String viewToOpen) throws IOException
   {
      Scene scene = null;
      FXMLLoader loader = new FXMLLoader();
      Parent root = null;

      if ("GroundRadar".equals(viewToOpen))
      {
         loader.setLocation(getClass().getResource("GroundRadarView.fxml"));
         root = loader.load();
         GroundRadarView view = loader.getController();
         view.init(mvViewModel.getGroundRadarViewModel());
         stage.setTitle("GroundRadar");
      }
      
      Stage localStage = new Stage();

      scene = new Scene(root);
      localStage.setScene(scene);
      localStage.show();
   }

}
