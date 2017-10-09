package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private Lock monLock;
    private int readersActive;
    private int readersWaiting;
    private int writersActive;
    private int writersWaiting;
    private Condition okToRead;
    private Condition okToWrite;

    public Monitor() {
        monLock = new ReentrantLock();
        okToRead = monLock.newCondition();
        okToWrite = monLock.newCondition();
    }

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
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
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (readersWaiting > 0){
                okToRead.signalAll();
            }
            else okToWrite.signal();
        } finally {
            monLock.unlock();
        }
    }

}
