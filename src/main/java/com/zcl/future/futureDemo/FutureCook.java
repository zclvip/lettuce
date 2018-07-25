package com.zcl.future.futureDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by zhaocl1 on 2018/7/13.
 */
public class FutureCook {

    static void cook(Chuju chuju,ShiCai shiCai){};
    static class Chuju{}
    static class ShiCai{}

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long starttime = System.currentTimeMillis();
        /**
         * Callabe接口可以看做是Runnable接口的补充，call方法带有返回值，可以抛出异常
         */
        Callable<Chuju> onLineShop = new Callable<Chuju>() {
            @Override
            public Chuju call() throws Exception {
                System.out.println("step 1 : buy chuju");
                System.out.println("step 1 : delivery");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("step 1 : sin");
                return new Chuju();
            }
        };

        FutureTask<Chuju> task = new FutureTask(onLineShop);
        new Thread(task).start();

        Thread.sleep(2000);
        ShiCai shiCai = new ShiCai();
        System.out.println("step 2 : shicai sign");

        if (!task.isDone()){
            System.out.println("chuju is deliverying ... please wait");
        }
        Chuju chuju = task.get();//get方法会阻塞线程，这里用的异步并关系执行结果，当异步时可以干点别的事

        System.out.println("step 3 cooking");
        cook(chuju, shiCai);
        System.out.println("total cost time :"+(System.currentTimeMillis() - starttime));
    }

    /**
     step 1 : buy chuju
     step 1 : delivery
     step 2 : shicai sign
     chuju is deliverying ... please wait
     step 1 : sin
     step 3 cooking
     total cost time :5018
     *
     */
}
