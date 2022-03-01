//package single.simplevacuumagent;
//
//import single.agent.Action;
//import single.agent.Agent;
//import single.agent.Percept;
//
///** The Vacuum Cleaning Agent - it is a simple reactive single.agent*/
//public class VacuumAgent extends Agent {
//
//	/** a deliberative single.agent stores its internal state - it may keep a history in the environment*/
//
//	private boolean dirt;
//	private boolean wall;
//
//	public void see(Percept p) {
//		if (((VacuumPercept) p).seeDirt())
//			dirt = true;
//		else
//			dirt = false;
//		if (((VacuumPercept) p).seeWall())
//			wall = true;
//		else
//			wall = false;
//	}
//
//	public Action selectAction() {
//		/** The single.agent action selection function */
//		if (dirt) {
//			return new SuckDirt();
//		}
//		if (wall)
//			return new TurnRight();
//		/** The single.agent has no strategy, it selects randomly an action */
//		int r = (int) Math.floor(Math.random() * 2);
//		switch (r) {
//		case 0:
//			return new GoForward();
//		case 1:
//			return new TurnRight();
//		}
//		return null;
//	}
//}