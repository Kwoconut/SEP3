package server;

public class Clock implements Runnable{
	
	private SimulationManager manager;
	private int hours;
	private int minutes;
	private int seconds;

	public Clock(SimulationManager manager)
	{
		this.manager = manager;
		hours=0;
		minutes=0;
		seconds=0;
	}
	@Override
	public void run() 
	{
		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds++;
			if(seconds==60)
			{
				minutes++;
				seconds=0;
				if(minutes==60)
				{
					hours++;
					minutes=0;
				}
			}
			System.out.println("Hours:"+ hours+"    Minutes"+minutes+"    Seconds"+seconds);
		}
	}

}
