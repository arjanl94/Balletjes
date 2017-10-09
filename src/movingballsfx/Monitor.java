package movingballsfx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Lock monLock = new ReentrantLock();
    private int readersActive;
    private int writersActive;

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0) {
                okToRead.await();
            }
            readersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) okToWrite.signal();
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) okToWrite.await();
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }
}
