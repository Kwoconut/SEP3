package server;

public class SimulationManager {

	private Server server;
	private boolean exitPlaneDispatcher;
	private boolean exitSimulationTimer;

	public SimulationManager(Server server) {
		this.server = server;
	}

	public void airPlaneDispatcherRun() {
		AirPlaneDispatcher airPlaneDispatcher = new AirPlaneDispatcher(this);
		Thread planeDispatcherThread = new Thread(airPlaneDispatcher);
		planeDispatcherThread.start();
	}
	
	public void groundPlaneDispatcherRun() {
		GroundPlaneDispatcher groundPlaneDispatcher = new GroundPlaneDispatcher(this);
		Thread planeDispatcherThread = new Thread(groundPlaneDispatcher);
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
