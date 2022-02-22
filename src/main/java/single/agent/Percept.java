package single.agent;

/** An abstract class for things an single.agent can perceive. Since an
    single.agent only receives one Percept per turn, the Percept may
    incorporate the results of multiple sensors. */
public abstract class Percept {

  /** Constructs a percept for a state from the point of view of a
      particular single.agent. Every subclass of Percept should override
      this default constructor. */
  public Percept(State s, Agent a) {

  }

  public abstract String toString();
}
