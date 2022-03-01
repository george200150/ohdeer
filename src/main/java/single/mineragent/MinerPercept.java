package single.mineragent;

import single.agent.Agent;
import single.agent.Percept;


/** A percept in the gold-mining world. */
public class MinerPercept extends Percept {

	protected boolean gold;
	protected boolean inrange;
	protected boolean hill;

	/**
	 * Constructs a gold-mining world percept. If the single.agent has in its range the gold, then it creates
	 * a percept that sees the gold. If close enough, it tells the miner that it can mine the gold.
	 */
	public MinerPercept(MinerState state, Agent agent) {

		super(state, agent);

		int x, y;

		x = state.getAgentX(agent.getUniqId());
		y = state.getAgentY(agent.getUniqId());

		/** determine gold range */
		if (state.isGoldInEyesight(x, y)) {
			inrange = state.canMineGold(x, y);
			gold = true;
		} else {
			gold = false;
			inrange = false;
		}
		hill = state.isHill(x, y);

	}

	/**
	 * Returns true if the percept reflects that the single.agent has in LOS the gold.
	 */
	public boolean seeGold() {
		return gold;
	}

	/**
	 * Returns true if the percept reflects that the single.agent has in pickaxe's range the gold.
	 */
	public boolean lockOnGold() {
		return inrange;
	}

	public String toString() {
		StringBuffer pstring;

		pstring = new StringBuffer(5);
		if (gold || inrange)
			pstring.append("GOLD ");
		else if (hill)
			pstring.append("HILL ");
		else
			pstring.append("CLEAR ");
		return pstring.toString();
	}

}
