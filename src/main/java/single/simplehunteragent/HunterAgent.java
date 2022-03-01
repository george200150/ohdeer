package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Percept;


/** The Hunter Agent - it is a simple reactive single.agent*/
public class HunterAgent extends Agent implements ObserverAgent {

	/**
	 * a deliberative single.agent stores its internal state - it may keep a history in the environment
	 */
	private boolean deer;
	private boolean inrange;

	int cachedObjectiveX = -1;
	int cachedObjectiveY = -1;
	int cachedTurnsRemaining = 0;

	public HunterAgent(int uniqId) {
		super(uniqId);
	}

	public void see(Percept p) {
		deer = ((HunterPercept) p).seeDeer();
		inrange = ((HunterPercept) p).lockDeer();
	}

	/**
	 * The single.agent action selection function
	 */
	public Action selectAction() {
		if (deer) {
			if (inrange) {
				return new ShootDeer(this.uniqId);
			}

			if (cachedObjectiveX == -1 && SharedMemory.getInstance().getDeerX() == -1 ||
					SharedMemory.getInstance().getDeerX() != cachedObjectiveX ||
					SharedMemory.getInstance().getDeerY() != cachedObjectiveY) {
				return new AnnounceDeerLocation(this.uniqId);
			}
			if (cachedObjectiveX != -1 && SharedMemory.getInstance().getDeerX() != -1) // deer was spotted; check shm
				return new DirectedSeek(this.uniqId);
			else
				return new RandomSeek(this.uniqId); // if objective has been reached, then resume to random movement
		}
		return new RandomSeek(this.uniqId);
	}

	@Override
	public void sendDeerData(int deerX, int deerY) {
		this.cachedObjectiveX = deerX;
		this.cachedObjectiveY = deerY;
	}

}
