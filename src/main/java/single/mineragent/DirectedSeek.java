package single.mineragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;

import java.util.Random;


public class DirectedSeek extends Action {

    public DirectedSeek(int uniqId) {
        super(uniqId);
    }

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
        int goldX, goldY;
        MinerState state = null;
        MinerAgent agent = null;

        if (s instanceof MinerState && a instanceof MinerAgent) {
            state = (MinerState) s;
            agent = (MinerAgent) a;
        }
        else
            System.out.println("ERROR - Argument to DirectedSeek.execute() is not of type MinerState");

        x = state.getAgentX(uniqId);
        y = state.getAgentY(uniqId);

        SharedMemory shm = SharedMemory.getInstance();

        goldX = shm.getGoldX();
        goldY = shm.getGoldY();
        int noTurns = manhattenWithDiagonal(x, y, goldX, goldY); // speed == 1unit/turn, so it does not matter here
        System.out.println("noTurns = " + noTurns);

        // we have a local cache in MinerAgent (x, y)
         if (agent.cachedObjectiveX == shm.getGoldX() &&
                 agent.cachedObjectiveY == shm.getGoldY()) {
             agent.cachedTurnsRemaining = noTurns;
             state = moveTowardsObjective(agent, state);
             return state;
         }

        // Here, we know for sure that shm must have newer information and we move our course towards another objective.
        // compute number of turns it takes to reach gold location
        Random rand = new Random();
        // possibility to intercept gold movement as turns go on (don't follow, meet head on)
        // inaccuracy rate proportional with the number of turns it takes a miner to reach the gold => radius of circle
        // decide where to go based on the circle of possible future locations of the gold (don't bother about turns++)
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

    private MinerState moveTowardsObjective(MinerAgent agent, MinerState state) {
        int finalX = agent.cachedObjectiveX;
        int finalY = agent.cachedObjectiveY;

        int dx = finalX - state.getAgentX(uniqId);
        int dy = finalY - state.getAgentY(uniqId);

        int stepX = sign(dx);
        int stepY = sign(dy);  // allow (+/-) (1,1), (1,0) or (0,1) movement

        int newX = state.getAgentX(uniqId) + stepX;
        int newY = state.getAgentY(uniqId) + stepY;

        if (state.inBounds(newX, newY) && !state.isHill(newX, newY)) {
            state.setAgentX(newX, uniqId);
            state.setAgentY(newY, uniqId);
        }
        return state;
    }

    public String toString() {
        return "DIRECTED SEEK";
    }

}
