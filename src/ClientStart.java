import javafx.application.Application;

public class ClientStart
{
   public static void main(String[] args)
   {
     new Thread(() -> Application.launch(ClientSetUp.class)).start();
   }
}
