package com.zcl.future;

import com.google.common.util.concurrent.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class ListenableFutureTest {
    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    public static void main(String[] args) {
//        listenableFutureTest();
//        listenableFutureTest2();
//        listenableFutureTest3();
        listenableFutureTest4();
    }


    //单个任务
    public static void listenableFutureTest(){
        final List<String> value = Collections.synchronizedList(new ArrayList<String>());
//        List<ListenableFuture<String>> futures = new ArrayList<>();
        ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call .....");
                TimeUnit.SECONDS.sleep(1);
                return "sleep one second";
            }
        });

        //方法一下 添加listener 获取结果
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("listener get listenableFuture.get = "+listenableFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        },executorService);

        //添加回调函数
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println("success : " + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("failure !!! ");
            }
        });
    }

    //多个任务
    public static void listenableFutureTest2(){
        final List<String> value = Collections.synchronizedList(new ArrayList<String>());
        List<ListenableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int index = i;
            ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    long sleep = new Random().nextInt(200);
                    if (index == 5){
                        sleep = 2000;
                    }
                    System.out.println(index+" call ....sleep="+sleep);
                    TimeUnit.MILLISECONDS.sleep(sleep);
                    return index+" sleep "+sleep+" ms";
                }
            });

            //方法一下 添加listener 获取结果
            listenableFuture.addListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(index+ " listener get listenableFuture.get = "+listenableFuture.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            },executorService);

            //添加回调函数
            Futures.addCallback(listenableFuture, new FutureCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    System.out.println("success : "+ s);
                    value.add(s);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("failure !!! ");
                }
            });
            //将每次查询得到的listenableFuture放入到集合中
            futures.add(listenableFuture);
        }

        /**
         * 这里将所有的 listenableFuture形成一个新的 listenbaleFuture
         * 目的是为了异步阻塞，直到所有的listenableFuture都得到结果才继续当前线程
         * 这里get出来的是任务中用时最长的一个
         */
        ListenableFuture<List<String>> listListenableFuture = Futures.allAsList(futures);
        try {
            listListenableFuture.get();
            System.out.println("all sub-task are finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多个任务 如果要作为一个通用的方法，call（）方法的内容要进行封装，抽成单独方法，
     *
     */
    public static void listenableFutureTest3(){
        List<ListenableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int index = i;
            futures.add(executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    long sleep = new Random().nextInt(200);
                    if (index == 5){
                        sleep = 2000;
                    }
                    System.out.println(index+" call ....sleep="+sleep);
                    TimeUnit.MILLISECONDS.sleep(sleep);
                    return index+" sleep "+sleep+" ms";
                }
            }));
        }
        ListenableFuture<List<String>> listListenableFuture = Futures.allAsList(futures);
        try {
            listListenableFuture.get();
            System.out.println("all sub-task are finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * 多个任务 如果要作为一个通用的方法，call（）方法的内容要进行封装，抽成单独方法，或者把抽象一个工作类 实现Callabe接口
     *
     */
    public static void listenableFutureTest4(){
        List<ListenableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int index = i;
            futures.add(executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return internalCall(index);
                }
            }));
        }
        ListenableFuture<List<String>> listListenableFuture = Futures.allAsList(futures);
        try {
            listListenableFuture.get();
            System.out.println("all sub-task are finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static String internalCall(int index){
        long sleep = new Random().nextInt(200);
        if (index == 5){
            sleep = 2000;
        }
        System.out.println(index+" call ....sleep="+sleep);
        try {
            TimeUnit.MILLISECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return index+" sleep "+sleep+" ms";
    }
}
