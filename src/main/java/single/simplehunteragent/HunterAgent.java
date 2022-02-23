package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Percept;


/** The Vacuum Cleaning Agent - it is a simple reactive single.agent*/
public class HunterAgent extends Agent {

	/**
	 * a deliberative single.agent stores its internal state - it may keep a history in the environment
	 */

	private boolean deer;
	private boolean inrange;
	private boolean hill;

	private static boolean announced = false; // TODO: remove this when implementing MAS

	public void see(Percept p) {
		deer = ((HunterPercept) p).seeDeer();
		inrange = ((HunterPercept) p).lockDeer();
		hill = ((HunterPercept) p).seeHill();
	}

	public Action selectAction() {
		/** The single.agent action selection function */
		if (deer) {
			if (inrange) {
				return new ShootDeer();
			}
			// TODO: single-agent only (deadlock else)
			if (!announced) {
				announced = true;
				return new AnnounceDeerLocation();
			}
			return new DirectedSeek();
		}
		if (hill)
			return new RandomSeek();
		/** The single.agent has no strategy, it selects randomly an action */
		int r = (int) Math.floor(Math.random() * 2);
		switch (r) {
			case 0:
			case 1:
				return new RandomSeek();
		}
		return null;
	}
}