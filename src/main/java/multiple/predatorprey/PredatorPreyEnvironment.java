package multiple.predatorprey;

import multiple.agent.Agent;
import multiple.agent.Environment;
import multiple.agent.Percept;

public class PredatorPreyEnvironment extends Environment {
    @Override
    protected Percept getPercept(Agent a) {
        Percept p;

//        if (state instanceof HunterState) {
        if (state instanceof PredatorPreyState) {
//            p = new HunterPercept((HunterState) state, a);
            p = new PredatorPreyPercept((PredatorPreyState) state, a);
            System.out.println("Percept: " + p.toString());
            return p;
        }
//        else {
//            if (state instanceof DeerState) {
//                p = new DeerPercept((DeerState) state, a);
//                System.out.println("Percept: " + p.toString());
//                return p;
//            } else {
//                System.out.println("ERROR - state is not a VacuumState object.");
//                return null;
//            }
//        }
        else {
            System.out.println("ERROR - state is not a PredatorPreyState object.");
            return null;
        }
    }
}
