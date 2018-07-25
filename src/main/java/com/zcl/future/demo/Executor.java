package com.zcl.future.demo;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class Executor {

    public  final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));

    public List<Integer> executor(Collection<? extends Person> persons,ExecutorCallback executorCallback){
        ListenableFuture<List<Integer>> listListenableFuture = asynExecutor((List<Person>) persons, executorCallback);
        try {
            return listListenableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ListenableFuture<List<Integer>> asynExecutor(List<Person> persons,ExecutorCallback executorCallback){

        List<ListenableFuture<Integer>> futures = new ArrayList<>();
        for (Person person : persons){
            futures.add(executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return internalCall(person, executorCallback);
                }
            }));
        }
        return Futures.allAsList(futures);
    }

    public int internalCall(Person person ,ExecutorCallback executorCallback){
        return executorCallback.execute(person);
    }
}
