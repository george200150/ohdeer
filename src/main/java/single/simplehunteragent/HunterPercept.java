package single.simplehunteragent;

import single.agent.Agent;
import single.agent.Percept;


/** A percept in the predator-prey world. */
public class HunterPercept extends Percept {
	protected boolean deer;
	protected boolean inrange;
	protected boolean hill;

	/**
	 * Constructs a predator-prey world percept. If the single.agent has in its range the deer, then it creates
	 * a percept that sees the deer. If close enough, it tells the hunter that it can shoot the deer.
	 */
	public HunterPercept(HunterState state, Agent agent) {

		super(state, agent);

		int x, y;

		x = state.getAgentX();
		y = state.getAgentY();

		/** determine deer range */
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
	 * Returns true if the percept reflects that the single.agent has in LOS a deer.
	 */
	public boolean seeDeer() {
		return deer;
	}

	/**
	 * Returns true if the percept reflects that the single.agent has in gun's range a deer.
	 */
	public boolean lockDeer() {
		return inrange;
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
