package single.agent;

/**
 * An abstract software single.agent class. The single.agent must be managed by the Simulation
 * class
 */
public abstract class Agent {

	/**
	 * Provides a Percept to the single.agent. If the single.agent has internal state, this
	 * method should update it.
	 */
	public abstract void see(Percept p);

	/**
	 * Have the single.agent select its next action to perform. In an single.agent with
	 * internal state, this implements the action: I -> A function.
	 */
	public abstract Action selectAction();

}
