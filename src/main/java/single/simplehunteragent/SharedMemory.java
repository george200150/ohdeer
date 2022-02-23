package single.simplehunteragent;

import java.util.Date;

public class SharedMemory {

    private static int deerX;
    private static int deerY;
    private static long lastModified;
    private static int viewCount;

    private static int noHunters = 1;  // TODO: change for MAS (depends on run config)

    private static SharedMemory instance;

    private SharedMemory() {
        deerX = -1;
        deerY = -1;
        lastModified = 0L;
        viewCount = 0;
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
            lastModified = -1L;
        }
    }

    public long getLastModified() {
        return lastModified;
    }
}
