package com.zcl.CompletableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * Created by zhaocl1 on 2018/5/18.
 */
public class One {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        //thenApply 入参只有一个Function，它是函数式接口 它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。
        /**
         * 进行变换
         * public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);
         public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn);
         public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn,Executor executor);
         */
        String result = CompletableFuture.supplyAsync(()->"hello").thenApply(s -> s + " world").join();
        System.out.println(result);

        //thenAccept 是针对结果进行消耗 入参是Consumer 无返回值
        /**
         * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
         public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
         public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
         */
        System.out.println(CompletableFuture.supplyAsync(()->"ni ").thenAccept(s -> System.out.println(s +" hao")));

        /**
         * 对上一步的计算结果不关心，执行下一个操作
         * public CompletionStage<Void> thenRun(Runnable action);
         public CompletionStage<Void> thenRunAsync(Runnable action);
         public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
         */
        CompletableFuture.supplyAsync(() ->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello worldQQ"));
        while (true){}
    }

    public static void thenCombine(){
        /**
         * 结合两个CompletionState的结果，进行转化后返回
         *public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
         public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
         public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
         */

    }

}
