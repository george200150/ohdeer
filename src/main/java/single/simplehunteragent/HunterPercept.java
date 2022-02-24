package single.simplehunteragent;

import single.agent.Agent;
import single.agent.Percept;


/** A percept in the vacuum cleaning world. */
public class HunterPercept extends Percept {
	protected boolean deer;
	protected boolean inrange;
	protected boolean hill;

	/**
	 * Constructs a vacuum world percept. If the single.agent is in a square that has
	 * dirt, then creates a percept that sees dirt.
	 */
	public HunterPercept(HunterState state, Agent agent) {

		super(state, agent);

		int x, y;

		x = state.getAgentX();
		y = state.getAgentY();

		/** determine dirt */
		if (state.isDeerInEyesight(x, y)) {
			inrange = state.isDeerInGunsight(x, y);
			deer = true;
		} else {
			deer = false;
			inrange = false;
		}
		hill = state.isHill(x, y);

	}


	/**
	 * Returns true if the percept reflects that the single.agent is over dirt.
	 */
	public boolean seeDeer() {
		return deer;
	}

	public boolean lockDeer() {
		return inrange;
	}

	/**
	 * Returns true if the percept reflects that the square immediately in front
	 * of the single.agent contains an obstacle.
	 */
	public boolean seeHill() {
		return hill;
	}

	public String toString() {

		StringBuffer pstring;

		pstring = new StringBuffer(5);
		if (deer || inrange)
			pstring.append("DEER ");
		else if (hill)
			pstring.append("HILL ");
		else
			pstring.append("CLEAR ");
		return pstring.toString();
	}
}
