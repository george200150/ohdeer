package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;

import java.util.Random;


public class DirectedSeek extends Action {

    int manhattenWithDiagonal(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int min = Math.min(dx, dy);
        int max = Math.max(dx, dy);

        int diagonalSteps = min;
        int straightSteps = max - min;

        return (int) Math.sqrt(2) * diagonalSteps + straightSteps - 1; // tile count = noTurns + 1
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

        deerX = shm.getDeerX();
        deerY = shm.getDeerY();
        int noTurns = manhattenWithDiagonal(x, y, deerX, deerY); // speed == 1unit/turn, so it does not matter here
        System.out.println("noTurns = " + noTurns);

        // we have a local cache in HunterAgent (x,y, lastModified)
        // if lastModified is less recent than the one from shm, then update local cache
         if (agent.cachedTurnsRemaining > 0 &&
                 agent.cachedObjectiveX == shm.getDeerX() &&
                 agent.cachedObjectiveY == shm.getDeerY()) {
             agent.cachedTurnsRemaining = noTurns;
             state = moveTowardsObjective(agent, state);
             return state;
         }

        // Here, we know for sure that shm must have newer information and we move our course towards another objective.
        // compute number of turns it takes to reach deer location
        Random rand = new Random();
        // possibility to intercept deer movement as turns go on (don't follow, meet head on)
        // number of turns * (deer) speed => radius of circle
        // decide where to go based on the circle of possible future locations of the deer (don't bother about turns++)
        int tries = 0;
        while (tries < 100) {
            int drandX = rand.nextInt(noTurns+1);
            int drandY = noTurns - drandX;

            int newX = x + drandX;
            int newY = y + drandY;

            if (state.inBounds(newX, newY) && !state.isHill(newX, newY) && !(newX == x && newY == y)) {
                agent.cachedObjectiveX = newX;
                agent.cachedObjectiveY = newY;
                agent.cachedTurnsRemaining = noTurns;
                break;
            }
            tries++;
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

        int newX = state.getAgentX() + stepX;
        int newY = state.getAgentY() + stepY;

        if (state.inBounds(newX, newY) && !state.isHill(newX, newY)) {
            state.setAgentX(newX);
            state.setAgentY(newY);
        }

        return state;
    }


    public String toString() {
        return "DIRECTED SEEK";
    }
}
