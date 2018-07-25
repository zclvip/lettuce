package com.zcl.lock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by zhaocl1 on 2018/7/25.
 * 利用同步工具，设计同步工具
 * 在同一时刻，只允许最多两个线程同时访问，超过两个线程将被阻塞
 */
public class TwinsLock implements Lock{

    Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    public Collection<Thread> getQuuredThread(){
        return sync.getQueuedThreads();
    }

    public int getQuuredlength(){
        return sync.getQueueLength();
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count <= 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;){
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;){
                int current = getState();
                int newCurrent = current + returnCount;
                if (compareAndSetState(current,newCurrent)){
                    return true;
                }
            }
        }


    }
}
