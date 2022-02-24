package single.simplehunteragent;

import org.junit.After;
import org.junit.Test;
import single.agent.Action;
import single.agent.Percept;

import java.util.Date;

import static org.junit.Assert.*;

public class ActionTest {

    private static final int CLEAR = 0;
    private static final int DEER = 1;
    private static final int HILL = 2;

    private static int[][] stuckConfiguration = {
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, CLEAR, HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
    };

    private static int[][] stuckConfigurationWithDeer = {
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, CLEAR, HILL, HILL},
            { HILL, HILL, HILL,  HILL, HILL},
            { HILL, HILL, HILL,  HILL, DEER},
    };

    private static int[][] mapConfiguration = {
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, DEER,  CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, CLEAR, HILL },
            { HILL, HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL,  HILL }
    };

    @After
    public void tearDown() {
        SharedMemory.getInstance().reset();
    }

    @Test
    public void testAnnounceDeerEffectInSharedMemory() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState.setDefaultMap(stuckConfigurationWithDeer);
        HunterState initState = HunterState.getInitState();
        initState.setAgentX(2);
        initState.setAgentY(2);
        env.setInitialState(initState);

        SharedMemory shm = SharedMemory.getInstance();
        assertEquals("Deer location is yet unkown!", shm.getDeerX(), -1);
        assertEquals("Deer location is yet unkown!", shm.getDeerY(), -1);
        assertEquals("Default Last Modified is 0L!", shm.getLastModified(), 0L);
        long timestamp = new Date().getTime();

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Should have seen the stuck deer!", "DEER ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceDeerLocation);
        env.updateState(agent, action);

        assertEquals("Deer is at (4,4)!", 4, shm.getDeerX());
        assertEquals("Deer is at (4,4)!", 4, shm.getDeerY());
        assertTrue("Modification should have been made between timestamp and now!",
                shm.getLastModified() <= new Date().getTime() && shm.getLastModified() >= timestamp);
    }

    @Test
    public void testDirectedSeekStuck() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState.setDefaultMap(stuckConfigurationWithDeer);
        HunterState initState = HunterState.getInitState();
        initState.setAgentX(2);
        initState.setAgentY(2);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Should have seen the stuck deer!", "DEER ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceDeerLocation);
        env.updateState(agent, action);

        Percept p1 = env.getPercept(agent);
        agent.see(p1);
        assertEquals("Should have seen the stuck deer again!", "DEER ", p.toString());

        Action action1 = agent.selectAction();
        assertTrue(action1 instanceof DirectedSeek);

        HunterState afterWalk = (HunterState) action1.execute(agent, env.currentState());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentX());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentY());
    }

    @Test
    public void testRandomSeekStuck() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState.setDefaultMap(stuckConfiguration);
        HunterState initState = HunterState.getInitState();
        initState.setAgentX(2);
        initState.setAgentY(2);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Hunter Should randomly seek the deer!", "CLEAR ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof RandomSeek);

        HunterState afterWalk = (HunterState) action.execute(agent, env.currentState());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentX());
        assertEquals("Should have not moved!", 2, afterWalk.getAgentY());
    }

    @Test
    public void testNotSeeDeer() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState.setDefaultMap(mapConfiguration);
        HunterState initState = HunterState.getInitState();
        initState.setAgentX(1);
        initState.setAgentY(1);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Hunter Should randomly seek the deer!", "CLEAR ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof RandomSeek);
    }

    @Test
    public void testSeeDeerOutOfGunRange() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState.setDefaultMap(mapConfiguration);
        HunterState initState = HunterState.getInitState();
        initState.setAgentX(4);
        initState.setAgentY(4);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Deer is not in range, even it should be!", "DEER ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof AnnounceDeerLocation);
    }


    @Test
    public void testSeeDeerInGunRange() {
        HunterAgent agent = new HunterAgent();
        SharedMemory.getInstance().addObserver(agent);
        HunterEnvironment env = new HunterEnvironment();

        HunterState initState = HunterState.getInitState();
        initState.setDefaultMap(mapConfiguration);
        initState.setAgentX(5);
        initState.setAgentY(5);
        env.setInitialState(initState);

        Percept p = env.getPercept(agent);
        agent.see(p);
        assertEquals("Deer is not in range, even it should be!", "DEER ", p.toString());

        Action action = agent.selectAction();
        assertTrue(action instanceof ShootDeer);
    }
}