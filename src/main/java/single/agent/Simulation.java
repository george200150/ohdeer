package single.agent;

import single.mineragent.MinerAgent;

/**
 * The top-level class for an single.agent simulation. This can be used for either
 * single or multi-single.agent simulations.
 */
public abstract class Simulation {

	protected Agent agent;
	protected Agent otherAgent;
	protected Environment env;

	/**
	 * Constructs a new simulation. Initializes the single.agent(or agents vector) and
	 * the environment.
	 */
	public Simulation(Environment e, Agent a, Agent d) {

		agent = a;
		otherAgent = d;
		env = e;

	}

	/**
	 * Runs the simulation starting from a given state. This consists of a
	 * sense-act loop for the/(each) single.agent. An alternative approach would be to
	 * allow the single.agent to decide when it will sense and act.
	 */
	public void start(State initState) {
		env.setInitialState(initState);
		env.currentState().display();

		Thread threadAgent1 = new Thread(() -> {
			while (!isComplete()) {
				Percept p = env.getPercept(agent);
				agent.see(p);
				Action action = agent.selectAction();
				env.updateState(agent, action);
				env.currentState().display();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread threadAgent2 = new Thread(() -> {
			while (!isComplete()) {
				Percept p = env.getPercept(otherAgent);
				otherAgent.see(p);
				Action action = otherAgent.selectAction();
				env.updateState(otherAgent, action);
				env.currentState().display();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		threadAgent1.start();
		threadAgent2.start();

		try {
			threadAgent1.join();
			threadAgent2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("END of simulation");

	}

	/** Is the simulation over? Returns true if it is, otherwise false. */
	protected abstract boolean isComplete();

}
