package multiple.deeragent;

import multiple.agent.Action;
import multiple.agent.Agent;
import multiple.agent.State;
import multiple.predatorprey.PredatorPreyState;

import java.util.Random;

public class RandomWalk extends Action {
    public RandomWalk() {

    }

    /**
     * Returns the state that results from the single.agent moving forward in the given
     * state. In order to avoid creating unnecessary objects, we do not create a
     * new state, but instead modify the old one. This would have to change if
     * the Environment needs to maintain a history of states.
     */
    public State execute(Agent a, State s) {

        int x, y;
        int newX, newY;
        PredatorPreyState state = null;

        if (s instanceof PredatorPreyState)
            state = (PredatorPreyState) s;
        else
            System.out
                    .println("ERROR - Argument to GoForward.execute() is not of type VacuumState");

        x = state.getAgentX(0);
        y = state.getAgentY(0);

        Random rand = new Random();
        newX = x + rand.nextInt(2) - 1;  // x +/-= 1
        newY = y + rand.nextInt(2) - 1;  // y +/-= 1

        if (state.inBounds(newX, newY)) {
            state.setAgentX(newX, 0);
            state.setAgentY(newY, 0);
        }

        return state;
    }

    public String toString() {
        return "Random Walk";
    }
}
