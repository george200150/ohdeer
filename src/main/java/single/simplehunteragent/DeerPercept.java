//package single.simplehunteragent;
//
//import single.agent.Agent;
//import single.agent.Percept;
//
//
//public class DeerPercept extends Percept {
//
//    protected boolean hunter;
//
//    public DeerPercept(HunterState state, Agent agent) {
//
//        super(state, agent);
//
//        int x, y;
//
//        x = state.getAgentX();
//        y = state.getAgentY();
//
//        /** determine deer range */
//        if (state.isHunterInEyesight(x, y)) {
//            hunter = true;
//        }
//        else {
//            hunter = false;
//        }
//    }
//
//    public boolean seeHunter() {
//        return hunter;
//    }
//
//    @Override
//    public String toString() {
//        StringBuffer pstring;
//
//        pstring = new StringBuffer(5);
//        if (hunter)
//            pstring.append("DEER ");
//        else
//            pstring.append("CLEAR ");
//        return pstring.toString();
//    }
//
//}
