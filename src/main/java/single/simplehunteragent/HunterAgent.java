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
	long cachedObjectiveLastModified = -1L;

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
				// TODO: should be careful here, as every two turns, this is called (due to shm being cleared)
				return new AnnounceDeerLocation();
				// known bug; this is called too often; must decide how to implement better check
				// if deer has changed position from last time, announce new position
				// TODO: check what stays in the shm and what is wiped out...
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
	public void sendDeerData(int deerX, int deerY, long lastModified) {
		this.cachedObjectiveX = deerX;
		this.cachedObjectiveY = deerY;
		//this.cachedObjectiveLastModified = lastModified; - this causes noTurn not to be calculated
		this.cachedTurnsRemaining = 2;  // TODO: may have problems with critical resources...
		// SharedMemory.getInstance().incrementViewCount() is NOT done here, for avoiding over-announcement
	}
}
