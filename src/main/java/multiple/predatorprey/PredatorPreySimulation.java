package multiple.predatorprey;

import multiple.agent.*;
import multiple.deeragent.DeerAgent;
import multiple.hunteragent.HunterAgent;

import java.util.ArrayList;
import java.util.List;

/** A simulator for the vacuum cleaning world environment. */
public class PredatorPreySimulation extends Simulation {

    public PredatorPreySimulation(PredatorPreyEnvironment env, DeerAgent deer, List<Agent> hunters) {
        super(env, deer, hunters);
    }

    /** The simulation is complete when the robot has cleaned all the dirt */
    protected boolean isComplete() {
        return !((PredatorPreyState) (env.currentState())).isDeerAlive();
    }

    /**
     * Starts the program.
     */
    public static void main(String[] args) {

        System.out.println("The Predator-Prey World Agent Test");
        System.out.println("-----------------------------------");
        System.out.println();

        List<Agent> hunters = new ArrayList<>();
        for (int ignored : new int[]{0, 1}) {  // TODO: 2 hunters atm
            HunterAgent hunter = new HunterAgent();
            hunters.add(hunter);
        }
        DeerAgent deer = new DeerAgent();

        PredatorPreyEnvironment env = new PredatorPreyEnvironment();
        PredatorPreySimulation sim = new PredatorPreySimulation(env, deer, hunters);
        PredatorPreyState initState = PredatorPreyState.getInitState();

        /** starts simulation */
        sim.start(initState);
    }

}
