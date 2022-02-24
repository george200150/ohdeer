package single.simplehunteragent;

import java.util.ArrayList;
import java.util.List;


public class SharedMemory {

    private static int deerX;
    private static int deerY;

    private static int noHunters;
    private final List<ObserverAgent> observers = new ArrayList<>();

    private static SharedMemory instance;

    private SharedMemory(int noH) {
        noHunters = noH;
        deerX = -1;
        deerY = -1;
    }

    public void addObserver(ObserverAgent agent){
        observers.add(agent);
    }

    public void broadcastDeerData(){
        for (ObserverAgent observer : observers) {
            observer.sendDeerData(deerX, deerY);
        }
    }

    public static SharedMemory getInstance() {
        if (instance == null) {
            instance = new SharedMemory(1);
        }
        return instance;
    }

    public int getDeerX() {
        return deerX;
    }

    public int getDeerY() {
        return deerY;
    }

    public void setDeerX(int x) {
        deerX = x;
    }

    public void setDeerY(int y) {
        deerY = y;
    }

    public void reset() {
        deerX = -1;
        deerY = -1;
        observers.removeIf((ignored) -> true); // remove all
    }

}
