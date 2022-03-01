package single.simplehunteragent;

import single.agent.Simulation;


/** A simulator for the predator-prey world environment. */
public class HunterSimulation extends Simulation {

//	public HunterSimulation(HunterEnvironment env, HunterAgent a, DeerAgent d) {
	public HunterSimulation(HunterEnvironment env, HunterAgent a, HunterAgent a1) {
//		super(env, a, d);
		super(env, a, a1);
	}

	/**
	 * The simulation is complete when the hunters have shot the deer
	 */
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

		HunterAgent a = new HunterAgent(0);
		HunterAgent a1 = new HunterAgent(1); // !!! strictly necessary that ids start from 0 and are consecutive.
//		DeerAgent d = new DeerAgent();
		SharedMemory.getInstance().addObserver(a);
		SharedMemory.getInstance().addObserver(a1);
		HunterEnvironment env = new HunterEnvironment();
		//DeerEnvironment envD = new DeerEnvironment();
		HunterSimulation sim = new HunterSimulation(env, a, a1);
		HunterState initState = HunterState.getInitState();

		/** starts simulation */
		sim.start(initState);
	}

}
