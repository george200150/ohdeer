package single.mineragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;


/**
 * A gold-mining world action that causes the single.agent to mine the gold in the range of the current location.
 */
public class MineGold extends Action {

	public MineGold(int uniqId) {
		super(uniqId);
	}

	/**
	 * Returns the state that results from the single.agent mining the gold in a 1 unit circle in the given state.
	 * In order to avoid creating unnecessary objects, we do not create a new state, but instead modify the old one.
	 * This would have to changed if the Environment needs to maintain a history of states.
	 */
	public State execute(Agent a, State s) {

		int x, y;
		MinerState state = null;

		if (s instanceof MinerState)
			state = (MinerState) s;
		else
			System.out.println("ERROR - Argument to MineGold.execute() is not of type MinerState");

		x = state.getAgentX(uniqId);
		y = state.getAgentY(uniqId);
		state.mineGold(x, y);
		return state;
	}

	public String toString() {
		return "MINE GOLD!";
	}

}
