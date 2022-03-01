package single.mineragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Environment;
import single.agent.Percept;


/** A simulator for the gold-mining world environment. */
public class MinerEnvironment extends Environment {

  public MinerEnvironment() {
  }

  /**
   * Creates a percept for an single.agent. This implements the see: S -> P
   * function.
   */
  protected Percept getPercept(Agent a) {
    MinerPercept p;

    if (state instanceof MinerState) {
      p = new MinerPercept((MinerState) state, a);
      System.out.println("Percept: " + p.toString());
      return p;
    } else {
      System.out.println("ERROR - state is not a MinerState object.");
      return null;
    }
  }

  /**
   * Executes an single.agent's action and update the environment's state.
   */
  protected void updateState(Agent a, Action action) {
    super.updateState(a, action);
    System.out.println("Action: " + action.toString());
    System.out.println();
  }

}
