package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.Environment;
import single.agent.Percept;


/** A simulator for the predator-prey world environment. */
public class HunterEnvironment extends Environment {

  public HunterEnvironment() {
  }

  /**
   * Creates a percept for an single.agent. This implements the see: S -> P
   * function.
   */
  protected Percept getPercept(Agent a) {
    HunterPercept p;
//    DeerPercept pd;

    if (state instanceof HunterState) {
//    if (state.hunter) {
      p = new HunterPercept((HunterState) state, a);
      System.out.println("Percept: " + p.toString());
      return p;
    } else {
//      pd = new DeerPercept((HunterState) state, a); // TODO: separate Hunter vs Deer States... but not for now.......
//      System.out.println("Percept: " + pd.toString());
//      return pd;
      System.out.println("ERROR !!!");
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
