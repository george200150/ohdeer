package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;

import java.util.Random;

public class DirectedSeek extends Action {

    private int euclidean(int x1, int x2, int y1, int y2) {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        return (int) Math.floor(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }

    @Override
    public State execute(Agent a, State s) {
        int x, y;
        int deerX, deerY;
        HunterState state = null;
        HunterAgent agent = null;

        if (s instanceof HunterState && a instanceof HunterAgent) {
            state = (HunterState) s;
            agent = (HunterAgent) a;
        }
        else
            System.out.println("ERROR - Argument to DirectedSeek.execute() is not of type HunterState");

        x = state.getAgentX();
        y = state.getAgentY();

        SharedMemory shm = SharedMemory.getInstance();


        // we have a local cache in HunterAgent (x,y, lastModified)
        // if lastModified is less recent than the one from shm, then update local cache
         if (agent.cachedObjectiveLastModified >= shm.getLastModified()) {
             state = moveTowardsObjective(agent, state);
             return state;
         }

        // Here, we know for sure that shm must have newer information and we move our course towards another objective.
        agent.cachedObjectiveLastModified = shm.getLastModified();

        deerX = shm.getDeerX();
        deerY = shm.getDeerY();
        shm.incrementViewCount();


        // compute number of turns it takes to reach deer location
        int noTurns = euclidean(x, deerX, y, deerY); // speed == 1unit/turn, so it does not matter here

        Random rand = new Random();
        // possibility to intercept deer movement as turns go on (don't follow, meet head on)
        // number of turns * (deer) speed => radius of circle
        // decide where to go based on the circle of possible future locations of the deer (don't bother about turns++)
        while (true) {
            int drandX = rand.nextInt(noTurns+1);
            int drandY = noTurns - drandX;

            int newX = x + drandX;
            int newY = y + drandY;

            if (state.inBounds(newX, newY)) {
                agent.cachedObjectiveX = newX;
                agent.cachedObjectiveY = newY;
                agent.cachedTurnsRemaining = noTurns; // TODO: may have less turns (because of euclidean/manhattan computation)
                break;
            }
        }

        state = moveTowardsObjective(agent, state);

        return state;
    }

    private int sign(int a) {
        if (a >= 0) {
            return 1;
        }
        return -1;
    }

    private HunterState moveTowardsObjective(HunterAgent agent, HunterState state) {
        int finalX = agent.cachedObjectiveX;
        int finalY = agent.cachedObjectiveY;

        int dx = finalX - state.getAgentX();
        int dy = finalY - state.getAgentY();

        int stepX = sign(dx);
        int stepY = sign(dy);  // allow (+/-) (1,1), (1,0) or (0,1) movement

        state.setAgentX(state.getAgentX() + stepX);
        state.setAgentY(state.getAgentY() + stepY);

        agent.cachedObjectiveLastModified -= 1;
        return state;
    }


    public String toString() {
        return "DIRECTED SEEK";
    }
}
