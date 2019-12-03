package server;

public class SimulationManager {

	private Server server;
	private Thread planeDispatcher;
	private Thread simulationState;
	private boolean exitPlaneDispatcher = false;

	public SimulationManager(Server server) {
		this.server = server;
	}

	public void planeDispatcherRun() {
		PlaneDispatcher planeDispatcher = new PlaneDispatcher(this);
		this.planeDispatcher = new Thread(planeDispatcher);
		this.planeDispatcher.start();
	}

	public void simulationStateRun() {
		SimulationState simulationState = new SimulationState(this);
		this.simulationState = new Thread(simulationState);
		this.simulationState.start();
	}
	
	public Server getServer()
	{
		return server;
	}
	
	public Thread getSimulationState()
	{
		return simulationState;
	}
	
	public Thread getPlaneDispatcher()
	{
		return planeDispatcher;
	}
	
	public boolean getExitPlaneDispatcher()
	{
		return exitPlaneDispatcher;
	}
	public void exitPlaneDispatcher()
	{
		exitPlaneDispatcher=true;
	}
}
