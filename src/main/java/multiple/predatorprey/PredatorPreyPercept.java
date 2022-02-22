package multiple.predatorprey;

import multiple.agent.Agent;
import multiple.agent.Percept;


public class PredatorPreyPercept extends Percept {

    protected boolean deer;
    protected boolean hill;

    /**
     * Constructs a vacuum world percept. If the single.agent is in a square that has
     * dirt, then creates a percept that sees dirt.
     */
    public PredatorPreyPercept(PredatorPreyState state, Agent agent) {

        super(state, agent);

        int[] xs, ys;

        xs = new int[]{state.getAgentX(0), state.getAgentX(1), state.getAgentX(2)};
        ys = new int[]{state.getAgentY(0), state.getAgentY(1), state.getAgentY(2)};

        for (int indx : new int[]{0, 1, 2}) {
            /** determine deer */
            if (state.hasDeer(xs[indx], ys[indx]))
                deer = true;
            else
                deer = false;

        }
    }

    /** Returns true if the percept reflects that the single.agent is over dirt. */
    public boolean seeDeer() {

        return deer;
    }

    /**
     * Returns true if the percept reflects that the square immediately in front
     * of the single.agent contains an obstacle.
     */
    public boolean seeHill() {

        return hill;
    }

    public String toString() {

        StringBuffer pstring;

        pstring = new StringBuffer(5);
        if (deer == true)
            pstring.append("DEER ");
        else if (hill == true)
            pstring.append("HILL ");
        else
            pstring.append("CLEAR ");
        return pstring.toString();
    }
}