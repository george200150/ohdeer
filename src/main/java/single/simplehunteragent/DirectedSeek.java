package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;

import java.util.Random;

public class DirectedSeek extends Action {
    @Override
    public State execute(Agent a, State s) {
        int newX, newY;
        HunterState state = null;

        if (s instanceof HunterState)
            state = (HunterState) s;
        else
            System.out.println("ERROR - Argument to DirectedSeek.execute() is not of type HunterState");


        SharedMemory shm = SharedMemory.getInstance();
        newX = shm.getDeerX();
        newY = shm.getDeerY();
        shm.incrementViewCount();

        // TODO: later compute number of turns it takes to reach location

        // TODO: later add possibility to intercept deer movement as turns go on (don't follow, meet head on)
        // number of turns * speed => radius of circle
        // decide where to go based on the circle of possible future locations of the deer (don't bother about turns++)

        // TODO: how to implement when to stop directed seek???
        // can have some local cache here... (x,y, lastModified; if lastModified < shm.lastModified then update; move)
        // when arriving at shm location, we delete the board ( - who wipes it? last? first? clumping problem...)
        // 1 is good enough for me...

        state.setAgentX(newX-1);
        state.setAgentY(newY);

        return state;
    }


    public String toString() {
        return "DIRECTED SEEK";
    }
}
