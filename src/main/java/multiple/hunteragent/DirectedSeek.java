package multiple.hunteragent;

import multiple.agent.Action;
import multiple.agent.Agent;
import multiple.agent.State;
import multiple.predatorprey.PredatorPreyState;

public class DirectedSeek extends Action {
    @Override
    public State execute(Agent a, State s) {
        int x, y;
//        HunterState state = null;
        PredatorPreyState state = null;

//        if (s instanceof HunterState)
        if (s instanceof PredatorPreyState)
//            state = (HunterState) s;
            state = (PredatorPreyState) s;
        else
            System.out
                    .println("ERROR - Argument to SuckDirt.execute() is not of type VacuumState");

        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
