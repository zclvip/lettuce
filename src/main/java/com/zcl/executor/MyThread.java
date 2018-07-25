package com.zcl.executor;

/**
 * Created by zhaocl1 on 2018/4/27.
 */
public class MyThread implements Runnable  {
    private int m_threadNumber;

    public MyThread(int m_threadNumber) {
        this.m_threadNumber = m_threadNumber;
        System.out.println("------m_threadNumber="+m_threadNumber);
    }

    public void run() {
        try {
            System.out.println("thread name :"+ Thread.currentThread().getName()+" " +m_threadNumber+" sleep start");
            Thread.sleep(1000);
            System.out.println(m_threadNumber + " sleep      end");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
