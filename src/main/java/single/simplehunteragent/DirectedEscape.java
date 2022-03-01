//package single.simplehunteragent;
//
//import single.agent.Action;
//import single.agent.Agent;
//import single.agent.State;
//
//
//public class DirectedEscape extends Action {
//
//    @Override
//    public State execute(Agent a, State s) {
//        HunterState state = null;
//
//        if (s instanceof HunterState && a instanceof DeerAgent) {
//            state = (HunterState) s;
//        }
//        else
//            System.out.println("ERROR - Argument to DirectedSeek.execute() is not of type HunterState");
//
//        state = runFromHunter(state);
//
//        return state;
//    }
//
//    private int sign(int a) {  // TODO: WET
//        if (a >= 0) {
//            return 1;
//        }
//        return -1;
//    }
//
//    private int reflection(int of, int withRespectTo) { // todo: should run in a smarter manner
//        int delta = withRespectTo - of;
//        return withRespectTo + delta;
//    }
//
//    private HunterState runFromHunter(HunterState state) {
//        int x = state.getDeerX();
//        int y = state.getDeerY();
//
//        int hunterX = state.getAgentX();
//        int hunterY = state.getAgentY();
//
//        int finalX = reflection(hunterX, x);
//        int finalY = reflection(hunterY, y);
//
//        int dx = finalX - state.getAgentX();
//        int dy = finalY - state.getAgentY();
//
//        int stepX = sign(dx);
//        int stepY = sign(dy);  // allow (+/-) (1,1), (1,0) or (0,1) movement
//
//        int newX = state.getDeerX() + stepX;
//        int newY = state.getDeerY() + stepY;
//
//        if (state.inBounds(newX, newY) && !state.isHill(newX, newY)) {
//            state.setDeerX(newX);
//            state.setDeerY(newY);
//        }
//        return state;
//    }
//
//    @Override
//    public String toString() {
//        return "DIRECTED DEER ESCAPE";
//    }
//}
