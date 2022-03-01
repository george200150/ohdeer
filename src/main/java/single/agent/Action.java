package single.agent;

/** An abstract class for actions in an single.agent environment. Each type of
 Action should be a separate subclass.  */
public abstract class Action {

  protected int uniqId;

  public Action(int uniqId) {
    this.uniqId = uniqId;
  }

  /** Update the state of the environment to reflect the effects of the
   single.agent performing the action. This implements the env: S x A -> S
   function. Note that in a multiagent environment, it is also
   important to know which single.agent is executing the action. */
  public abstract State execute(Agent a, State s);

  public abstract String toString();
}
