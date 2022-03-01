package single.mineragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Percept;


/** The Miner Agent - it is a simple reactive single.agent*/
public class MinerAgent extends Agent implements ObserverAgent {

	/**
	 * a deliberative single.agent stores its internal state - it may keep a history in the environment
	 */
	private boolean gold;
	private boolean inrange;

	int cachedObjectiveX = -1;
	int cachedObjectiveY = -1;
	int cachedTurnsRemaining = 0;

	public MinerAgent(int uniqId) {
		super(uniqId);
	}

	public void see(Percept p) {
		gold = ((MinerPercept) p).seeGold();
		inrange = ((MinerPercept) p).lockOnGold();
	}

	/**
	 * The single.agent action selection function
	 */
	public Action selectAction() {
		if (gold) {
			if (inrange) {
				return new MineGold(this.uniqId);
			}

			if (cachedObjectiveX == -1 && SharedMemory.getInstance().getGoldX() == -1 ||
					SharedMemory.getInstance().getGoldX() != cachedObjectiveX ||
					SharedMemory.getInstance().getGoldY() != cachedObjectiveY) {
				return new AnnounceGoldLocation(this.uniqId);
			}
		}
		if (cachedObjectiveX != -1 && SharedMemory.getInstance().getGoldX() != -1) // gold was spotted; check shm
			return new DirectedSeek(this.uniqId);
		else
			return new RandomSeek(this.uniqId); // if objective has been reached, then resume to random movement
	}

	@Override
	public void sendGoldData(int goldX, int goldY) {
		this.cachedObjectiveX = goldX;
		this.cachedObjectiveY = goldY;
	}

}
