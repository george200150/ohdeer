package single.mineragent;

import single.agent.Simulation;


/** A simulator for the gold-mining world environment. */
public class MinerSimulation extends Simulation {

	public MinerSimulation(MinerEnvironment env, MinerAgent a, MinerAgent a1) {
		super(env, a, a1);
	}

	/**
	 * The simulation is complete when the miners have mine the gold
	 */
	protected boolean isComplete() {
		return !((MinerState) (env.currentState())).isGoldRemaining();
	}

	/**
	 * Starts the program.
	 */
	public static void main(String[] args) {

		System.out.println("The Gold-Rush World Agent Test");
		System.out.println("-----------------------------------");
		System.out.println();

		MinerAgent a = new MinerAgent(0);
		MinerAgent a1 = new MinerAgent(1); // !!! strictly necessary that ids start from 0 and are consecutive.
		SharedMemory.getInstance().addObserver(a);
		SharedMemory.getInstance().addObserver(a1);
		MinerEnvironment env = new MinerEnvironment();
		MinerSimulation sim = new MinerSimulation(env, a, a1);
		MinerState initState = MinerState.getInitState();

		/** starts simulation */
		sim.start(initState);
	}

}
