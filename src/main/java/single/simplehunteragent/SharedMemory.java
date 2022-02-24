package single.simplehunteragent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SharedMemory {

    private static int deerX;
    private static int deerY;
    private static long lastModified;
    private static int viewCount;

    private static int noHunters = 1;  // TODO: change for MAS (depends on run config)
    private final List<ObserverAgent> observers = new ArrayList<>();

    private static SharedMemory instance;

    private SharedMemory() {
        deerX = -1;
        deerY = -1;
        lastModified = 0L;
        viewCount = 0;
    }

    public void addObserver(ObserverAgent agent){
        observers.add(agent);
    }

    public void broadcastDeerData(){
        for (ObserverAgent observer : observers) {
            observer.sendDeerData(deerX, deerY, lastModified); // viewCount will be incremented when hunters move
        }
    }

    public static SharedMemory getInstance() {
        if (instance == null) {
            instance = new SharedMemory();
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

    public void setLastModified() {
        lastModified = new Date().getTime();
        viewCount = 1; // because one hunter already knows about it
    }

    public void incrementViewCount() {
        viewCount += 1;
        if (viewCount >= noHunters) {  // clear broadcasted information
            deerX = -1;
            deerY = -1;
            viewCount = 0;
            //lastModified = -1L;  // TODO: decide if we could give up deleting this (only delete X,Y)
        }
    }

    public long getLastModified() {
        return lastModified;
    }

    public void reset() {
        deerX = -1;
        deerY = -1;
        lastModified = 0L;
        viewCount = 0;
        observers.removeIf((ignored) -> true); // remove all
    }
}
