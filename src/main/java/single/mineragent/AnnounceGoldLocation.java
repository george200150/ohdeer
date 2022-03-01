package single.mineragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;


public class AnnounceGoldLocation extends Action {

    public AnnounceGoldLocation(int uniqId) {
        super(uniqId);
    }

    @Override
    public State execute(Agent a, State s) {

        int x, y;
        MinerState state = null;

        if (s instanceof MinerState)
            state = (MinerState) s;
        else
            System.out.println("ERROR - Argument to RandomSeek.execute() is not of type MinerState");

        x = state.getAgentX(uniqId);
        y = state.getAgentY(uniqId);

        SharedMemory shm = SharedMemory.getInstance();
        shm.setGoldX(state.getGoldX(x, y));
        shm.setGoldY(state.getGoldY(x, y));
        shm.broadcastGoldData();
        return state;
    }

    @Override
    public String toString() {
        return "ANNOUNCED GOLD";
    }

}
