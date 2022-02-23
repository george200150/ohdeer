package single.simplehunteragent;

import single.agent.Simulation;

/** A simulator for the vacuum cleaning world environment. */
public class HunterSimulation extends Simulation {

	public HunterSimulation(HunterEnvironment env, HunterAgent a) {
		super(env, a);
	}

	/** The simulation is complete when the robot has cleaned all the dirt */
	protected boolean isComplete() {
		return !((HunterState) (env.currentState())).getIsDeerAlive();
	}

	/**
	 * Starts the program.
	 */
	public static void main(String[] args) {

		System.out.println("The Vacuum Cleaner World Agent Test");
		System.out.println("-----------------------------------");
		System.out.println();

		HunterAgent a = new HunterAgent();
		HunterEnvironment env = new HunterEnvironment();
		HunterSimulation sim = new HunterSimulation(env, a);
		HunterState initState = HunterState.getInitState();

		/** starts simulation */
		sim.start(initState);
	}

}
