package single.agent;


/** A complete representation of a situation in the single.agent environment.
    Since this is very domain specific, few methods are given.
    However, there should be methods for updating and retrieving various
    aspects of the state. */
public abstract class State {

  public boolean hunter = true;

  /** Displays information about the state. This may be as simple as
      text-based output, or could update a graphical display. */
  public abstract void display();

}
