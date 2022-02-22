package multiple.agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SharedMemory {
    private int[] sharedKnowledge = new int[]{-1, -1};
    private long lastModifiedTime;
    private final Lock lock = new ReentrantLock(true);
    private final List<ObserverAgent> subscribers = new ArrayList();

    private SharedMemory() {
        Date date = new Date();
        lastModifiedTime = date.getTime();
    }

    private void notifyAllSubscribers() {
        for (ObserverAgent subscriber : this.subscribers) {
            subscriber.notifySharedInfo();
        }
        // TODO: delete information
    }

    public void share(int[] personal_knowledge, long informationTime){
        lock.lock();
        try {
            // TODO: check if information is written there
            //      if so,
            //          if your information is older, then don't share it anymore
            //          else overwrite the last shared information and notify subscribers
            //      else, write information (as there is none there)
            if (this.lastModifiedTime < informationTime) {
                this.sharedKnowledge = personal_knowledge;
                notifyAllSubscribers();
            }

            // must set the information's timestamp since last modification (shared resource)
            // TODO: emit a notification if new information is written

            // TODO: every time a notification of new information is sent, subscribers must:
            //      - increment a counter k if k <= #subscribers, n
            //      - delete the shared information if k == n and set k to 1
        } catch (Exception e) {
            // handle the exception
            System.out.println(e.getMessage());  // idk when this happens
        } finally {
            lock.unlock();

            // TODO: decide if the sender of notification also increments k (shared resource)
        }
    }
}
