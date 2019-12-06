package server;

public class SimulationManager {

	private Server server;
	private boolean exitPlaneDispatcher;
	private boolean exitSimulationTimer;

	public SimulationManager(Server server) {
		this.server = server;
	}

	public void planeDispatcherRun() {
		PlaneDispatcher planeDispatcher = new PlaneDispatcher(this);
		Thread planeDispatcherThread = new Thread(planeDispatcher);
		planeDispatcherThread.start();
	}

	public void simulationStateRun() {
		SimulationState simulationState = new SimulationState(this);
		Thread simulationStateThread = new Thread(simulationState);
		simulationStateThread.start();
	}
	
	public void simulationTimerRun()
	{
	   SimulationTimer simulationTimer = new SimulationTimer(this);
	   Thread simulationTimerThread = new Thread(simulationTimer);
	   simulationTimerThread.start();
	}
	
	public Server getServer()
	{
		return server;
	}
	
	public boolean getExitPlaneDispatcher()
	{
		return exitPlaneDispatcher;
	}
	
	public void exitPlaneDispatcher()
	{
		exitPlaneDispatcher=true;
	}
	
	public boolean getSimulationTimer()
	{
	   return exitSimulationTimer;
	}
	
	public void exitSimulationTimer()
	{
	   exitSimulationTimer=true;
	}
}
