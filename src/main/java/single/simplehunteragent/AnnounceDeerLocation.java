package single.simplehunteragent;

import single.agent.Action;
import single.agent.Agent;
import single.agent.State;


public class AnnounceDeerLocation extends Action {

    public AnnounceDeerLocation(int uniqId) {
        super(uniqId);
    }

    @Override
    public State execute(Agent a, State s) {

        int x, y;
        HunterState state = null;

        if (s instanceof HunterState)
            state = (HunterState) s;
        else
            System.out.println("ERROR - Argument to RandomSeek.execute() is not of type HunterState");

        x = state.getAgentX(uniqId);
        y = state.getAgentY(uniqId);

        SharedMemory shm = SharedMemory.getInstance();
        shm.setDeerX(state.getDeerX(x, y));
        shm.setDeerY(state.getDeerY(x, y));
        shm.broadcastDeerData();
        return state;
    }

    @Override
    public String toString() {
        return "ANNOUNCED DEER";
    }

}
