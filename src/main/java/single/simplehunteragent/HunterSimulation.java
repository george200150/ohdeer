package single.simplehunteragent;

import single.agent.Simulation;

/** A simulator for the predator-prey world environment. */
public class HunterSimulation extends Simulation {

	public HunterSimulation(HunterEnvironment env, HunterAgent a) {
		super(env, a);
	}

	/** The simulation is complete when the hunters have shot the deer */
	protected boolean isComplete() {
		return !((HunterState) (env.currentState())).isDeerAlive();
	}

	/**
	 * Starts the program.
	 */
	public static void main(String[] args) {

		System.out.println("The Predator-Prey World Agent Test");
		System.out.println("-----------------------------------");
		System.out.println();

		HunterAgent a = new HunterAgent();
		SharedMemory.getInstance().addObserver(a);
		HunterEnvironment env = new HunterEnvironment();
		HunterSimulation sim = new HunterSimulation(env, a);
		HunterState initState = HunterState.getInitState();

		/** starts simulation */
		sim.start(initState);
	}

}
