package single.mineragent;

import java.util.ArrayList;
import java.util.List;


public class SharedMemory {

    private static int goldX;
    private static int goldY;

    private static int noMiners;
    private final List<ObserverAgent> observers = new ArrayList<>();

    private static SharedMemory instance;

    private SharedMemory(int noH) {
        noMiners = noH;
        goldX = -1;
        goldY = -1;
    }

    public void addObserver(ObserverAgent agent) {
        observers.add(agent);
    }

    public void broadcastGoldData() {
        for (ObserverAgent observer : observers) {
            observer.sendGoldData(goldX, goldY);
        }
    }

    public static SharedMemory getInstance() {
        if (instance == null) {
            instance = new SharedMemory(1);
        }
        return instance;
    }

    public int getGoldX() {
        return goldX;
    }

    public int getGoldY() {
        return goldY;
    }

    public void setGoldX(int x) {
        goldX = x;
    }

    public void setGoldY(int y) {
        goldY = y;
    }

    public void reset() {
        goldX = -1;
        goldY = -1;
        observers.removeIf((ignored) -> true); // remove all
    }

}
