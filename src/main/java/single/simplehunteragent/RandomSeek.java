package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;

import java.util.Random;


/**
 * A predator-prey world action that causes the single.agent to move randomly in another location.
 */
public class RandomSeek extends Action {

	public RandomSeek() {
	}

	/**
	 * Returns the state that results from the single.agent moving to a random position in the given state.
	 * In order to avoid creating unnecessary objects, we do not create a new state, but instead modify the old one.
	 * This would have to changed if the Environment needs to maintain a history of states.
	 */
	public State execute(Agent a, State s) {

		int x, y;
		int newX, newY;
		HunterState state = null;

		if (s instanceof HunterState)
			state = (HunterState) s;
		else
			System.out.println("ERROR - Argument to RandomSeek.execute() is not of type HunterState");

		x = state.getAgentX();
		y = state.getAgentY();

		Random rand = new Random();

		int tries = 0;
		while (tries < 100) {
			newX = x + rand.nextInt(3) - 1;  // x +/-= 1
			newY = y + rand.nextInt(3) - 1;  // y +/-= 1

			if (state.inBounds(newX, newY) && !state.isHill(newX, newY) && !(newX == x && newY == y)) {
				state.setAgentX(newX);
				state.setAgentY(newY);
				break;
			}
			tries++;
		}

		return state;
	}

	public String toString() {
		return "UNDIRECTED SEEK";
	}
}
