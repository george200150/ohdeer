package single.mineragent;

import org.junit.After;
import org.junit.Test;
import single.agent.Action;
import single.agent.Percept;


import static org.junit.Assert.*;

public class ActionTest {

    private static final int CLEAR = 0;
    private static final int GOLD = 1;
    private static final int HILL = 2;

    private static int[][] stuckConfiguration = {
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, CLEAR, HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
    };

    private static int[][] stuckConfigurationWithGold = {
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, CLEAR, HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, GOLD},
    };

    private static int[][] mapConfiguration = {
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, GOLD,  CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL }
    };

    @After
    public void tearDown() {
        SharedMemory.getInstance().reset();
    }

    @Test
    public void testAnnounceGoldEffectInSharedMemory() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState.setDefaultMap(stuckConfigurationWithGold);
        MinerState initState = MinerState.getInitState();
        initState.setAgentX(2, 0);
        initState.setAgentY(2, 0);
        env.setInitialState(initState);

        SharedMemory shm = SharedMemory.getInstance();
        assertEquals("Gold location is yet unknown!", shm.getGoldX(), -1);
        assertEquals("Gold location is yet unknown!", shm.getGoldY(), -1);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Should have seen the stuck gold!", "GOLD ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceGoldLocation);
        env.updateState(agent, action);

        assertEquals("Gold is at (4,4)!", 4, shm.getGoldX());
        assertEquals("Gold is at (4,4)!", 4, shm.getGoldY());
    }

    @Test
    public void testDirectedSeekStuck() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState.setDefaultMap(stuckConfigurationWithGold);
        MinerState initState = MinerState.getInitState();
        initState.setAgentX(2, 0);
        initState.setAgentY(2, 0);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Should have seen the stuck gold!", "GOLD ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceGoldLocation);
        env.updateState(agent, action);

        Percept p1 = env.getPercept(agent);
        agent.see(p1);
        assertEquals("Should have seen the stuck gold again!", "GOLD ", p.toString());

        Action action1 = agent.selectAction();
        assertTrue(action1 instanceof DirectedSeek);

        MinerState afterWalk = (MinerState) action1.execute(agent, env.currentState());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentX(0));
        assertEquals("Should have not moved!", 2, afterWalk.getAgentY(0));
    }

    @Test
    public void testRandomSeekStuck() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState.setDefaultMap(stuckConfiguration);
        MinerState initState = MinerState.getInitState();
        initState.setAgentX(2, 0);
        initState.setAgentY(2, 0);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Miner Should randomly seek the gold!", "CLEAR ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof RandomSeek);

        MinerState afterWalk = (MinerState) action.execute(agent, env.currentState());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentX(0));
        assertEquals("Should have not moved!", 2, afterWalk.getAgentY(0));
    }

    @Test
    public void testNotSeeGold() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState.setDefaultMap(mapConfiguration);
        MinerState initState = MinerState.getInitState();
        initState.setAgentX(1, 0);
        initState.setAgentY(1, 0);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Miner Should randomly seek the gold!", "CLEAR ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof RandomSeek);
    }

    @Test
    public void testSeeGoldOutOfGunRange() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState.setDefaultMap(mapConfiguration);
        MinerState initState = MinerState.getInitState();
        initState.setAgentX(4, 0);
        initState.setAgentY(4, 0);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Gold is not in range, even it should be!", "GOLD ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceGoldLocation);
    }


    @Test
    public void testSeeGoldInGunRange() {
        MinerAgent agent = new MinerAgent(0);
        SharedMemory.getInstance().addObserver(agent);
        MinerEnvironment env = new MinerEnvironment();

        MinerState initState = MinerState.getInitState();
        MinerState.setDefaultMap(mapConfiguration);
        initState.setAgentX(5, 0);
        initState.setAgentY(5, 0);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Gold is not in range, even it should be!", "GOLD ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof MineGold);
    }
}
