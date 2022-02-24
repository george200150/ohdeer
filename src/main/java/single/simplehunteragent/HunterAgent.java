package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Percept;


/** The Vacuum Cleaning Agent - it is a simple reactive single.agent*/
public class HunterAgent extends Agent implements ObserverAgent {

	/**
	 * a deliberative single.agent stores its internal state - it may keep a history in the environment
	 */

	private boolean deer;
	private boolean inrange;

	int cachedObjectiveX = -1;
	int cachedObjectiveY = -1;
	int cachedTurnsRemaining = 0;

	public void see(Percept p) {
		deer = ((HunterPercept) p).seeDeer();
		inrange = ((HunterPercept) p).lockDeer();
	}

	public Action selectAction() {
		/** The single.agent action selection function */
		if (deer) {
			if (inrange) {
				return new ShootDeer();
			}

			if (cachedObjectiveX == -1 && SharedMemory.getInstance().getDeerX() == -1 ||
					SharedMemory.getInstance().getDeerX() != cachedObjectiveX ||
					SharedMemory.getInstance().getDeerY() != cachedObjectiveY) {
				return new AnnounceDeerLocation();
			}
			if (cachedTurnsRemaining > 0)
				// if objective has been reached, then resume to random movement
				return new DirectedSeek();
			else
				return new RandomSeek();
		}
		return new RandomSeek();
	}

	@Override
	public void sendDeerData(int deerX, int deerY) {
		this.cachedObjectiveX = deerX;
		this.cachedObjectiveY = deerY;
		this.cachedTurnsRemaining = 2;  // TODO: may have problems with critical resources...
		// SharedMemory.getInstance().incrementViewCount() is NOT done here, for avoiding over-announcement
	}
}
