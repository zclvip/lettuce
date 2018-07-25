package com.zcl.executor;

import java.util.concurrent.*;

/**
 * Created by zhaocl1 on 2018/4/27.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
       pool2();
    }


    public static void pool2() throws InterruptedException {
        /**
         * 当线程数不满时，新来任务时新建线程池，满了之后进队列，队列满了之后
         */
        ExecutorService pool2 = new ThreadPoolExecutor(10,10,0L,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(10),new ThreadPoolExecutor.CallerRunsPolicy());
        long start = System.currentTimeMillis();
        int threadNumber = 0;
        for (int i = 0; i < 100; i++) {
            pool2.submit(new MyThread(threadNumber));
            threadNumber++;
            System.out.println("threadNumber="+threadNumber);
        }

        pool2.shutdown();
        pool2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("end "+(System.currentTimeMillis() -start)+" ms");
    }

    public static void pool1() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        long start = System.currentTimeMillis();
        int threadNumber = 0;
        for (int i = 0; i < 10; i++) {
            pool.submit(new MyThread(threadNumber));
            threadNumber++;
            System.out.println("threadNumber="+threadNumber);
        }

        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("end "+(System.currentTimeMillis() -start)+" ms");
    }
}
