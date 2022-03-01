//package single.simplehunteragent;
//
//import single.agent.Action;
//import single.agent.Percept;
//import single.agent.Agent;
//
//public class DeerAgent extends Agent {
//
//    private boolean hunter;
//
//    @Override
//    public void see(Percept p) {
//        hunter = ((DeerPercept) p).seeHunter();
//    }
//
//    @Override
//    public Action selectAction() {
//        if (hunter) {
//            return new DirectedEscape();
//        }
//        return new RandomSeek();
//    }
//
//}
