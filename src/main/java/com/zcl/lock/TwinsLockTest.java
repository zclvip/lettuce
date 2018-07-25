package com.zcl.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by zhaocl1 on 2018/7/25.
 */
public class TwinsLockTest {


    public static void main(String[] args) {
        final Lock lock = new TwinsLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    lock.lock();
                    int ql = ((TwinsLock)lock).getQuuredlength();
                    System.out.println("ql:"+ql);
                    try {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println("thread name :" + Thread.currentThread().getName());
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }

        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
