import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import model.Timer;

public class TestingShiet
{
   public static void main(String[] args) throws InterruptedException
   {
      Timer timer = new Timer(5,59,30);
      
      System.out.println(timer);
      
      int seconds = timer.convertToSeconds();
      
      Timer timer2 = new Timer(seconds);
      
      System.out.println(timer2);
   }
}
