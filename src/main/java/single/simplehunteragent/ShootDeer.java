package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;


/**
 * A predator-prey world action that causes the single.agent to shoot the deer in the range of the current location.
 */
public class ShootDeer extends Action {

	public ShootDeer() {
	}

	/**
	 * Returns the state that results from the single.agent shooting the deer in a 1 unit circle in the given state.
	 * In order to avoid creating unnecessary objects, we do not create a new state, but instead modify the old one.
	 * This would have to changed if the Environment needs to maintain a history of states.
	 */
	public State execute(Agent a, State s) {

		int x, y;
		HunterState state = null;

		if (s instanceof HunterState)
			state = (HunterState) s;
		else
			System.out.println("ERROR - Argument to ShootDeer.execute() is not of type HunterState");

		x = state.getAgentX();
		y = state.getAgentY();
		state.killDeer(x, y);
		return state;
	}

	public String toString() {
		return "SHOOT DEER!";
	}

}
