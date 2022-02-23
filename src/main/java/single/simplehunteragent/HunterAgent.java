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
//	private boolean hill;

	int cachedObjectiveX = -1;
	int cachedObjectiveY = -1;
	int cachedTurnsRemaining = 0;
	long cachedObjectiveLastModified = -1L;

	private static boolean announced = false; // TODO: remove this when implementing MAS

	public void see(Percept p) {
		deer = ((HunterPercept) p).seeDeer();
		inrange = ((HunterPercept) p).lockDeer();
//		hill = ((HunterPercept) p).seeHill();
	}

	public Action selectAction() {
		/** The single.agent action selection function */
		if (deer) {
			if (inrange) {
				return new ShootDeer();
			}
			// TODO: single-agent only (deadlock)
			if (!announced) { // TODO: could use cachedTurnsRemaining here, to avoid overannouncing
//			if (cachedTurnsRemaining == 0) {
				// (could use a separated action for noTurns computation - "Reorientation Action") that can be used here... or broadcasted... (which is the only way to nofity other hunters)
				cachedTurnsRemaining = 1; // quickfix (should be broadcasted instead)
				announced = true;
				return new AnnounceDeerLocation();
			}
			if (cachedTurnsRemaining > 0) // TODO: cachedTurnsRemaining must be computed before this part... in the broadcast would be the most suitable...
				// if objective has been reached, then resume to random movement
				return new DirectedSeek();
			else
				return new RandomSeek();
		}
		return new RandomSeek();
	}
}
