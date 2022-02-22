package multiple.hunteragent;

import multiple.agent.Action;
import multiple.agent.Agent;
import multiple.agent.Percept;
import multiple.deeragent.RandomWalk;
import multiple.predatorprey.PredatorPreyPercept;

public class HunterAgent extends Agent {
    /** a deliberative single.agent stores its internal state - it may keep a history in the environment*/

    private boolean deer;
    private boolean hill;

    public void see(Percept p) {
//        if (((HunterPercept) p).seeDeer())
        if (((PredatorPreyPercept) p).seeDeer())
            deer = true;
        else
            deer = false;
//        if (((HunterPercept) p).seeHill())
        if (((PredatorPreyPercept) p).seeHill())
            hill = true;
        else
            hill = false;
    }

    public Action selectAction() {
        /** The single.agent action selection function */
        if (deer) {
            return new DirectedSeek();
        }
        if (hill)
            return new RandomWalk();
        /** The single.agent has no strategy, it selects randomly an action */
        int r = (int) Math.floor(Math.random() * 2);
        switch (r) {
            case 0:
                return new RandomWalk();
            case 1:
                return new RandomWalk();
        }
        return null;
    }
}
