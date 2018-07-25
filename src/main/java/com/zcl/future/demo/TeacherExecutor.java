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
public class TeacherExecutor {
    public  final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
//    private Executor executor;
    private List<Teacher> teachers = new ArrayList<>();

    public TeacherExecutor(Executor executor, List<Teacher> teachers) {
//        this.executor = executor;
        this.teachers = teachers;
    }

    public TeacherExecutor(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public boolean executor(){
        ListenableFuture<List<Integer>> listListenableFuture = asynExecutor(teachers, new ExecutorCallback() {
            @Override
            public int execute(Person person) {
                return ((Teacher)person).teach();
            }
        });
        try {
            List<Integer> result =  listListenableFuture.get();
            for (Integer i : result){
                System.out.println("Teacher teach:"+i);
            }
            if (result != null && result.size() > 0){
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ListenableFuture<List<Integer>> asynExecutor(List<Teacher> teachers,ExecutorCallback executorCallback){

        List<ListenableFuture<Integer>> futures = new ArrayList<>();
        for (Teacher teacher : teachers){
            futures.add(executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return internalCall(teacher, executorCallback);
                }
            }));
        }
        return Futures.allAsList(futures);
    }

    public int internalCall(Teacher teacher ,ExecutorCallback executorCallback){
        return executorCallback.execute(teacher);
    }

//    public boolean executor(){
//        List<Integer> result = executor.executor(teachers, new ExecutorCallback() {
//            @Override
//            public int execute(Person person) {
//                return ((Teacher)person).teach();
//            }
//        });
//        for (Integer i : result){
//            System.out.println("student game:"+i);
//        }
//        if (result != null && result.size() > 0){
//            return true;
//        }
//        return false;
//    }

    public boolean executor2(){
        ListenableFuture<List<Integer>> listListenableFuture = asynExecutor(teachers);
        try {
            List<Integer> result =  listListenableFuture.get();
            for (Integer i : result){
                System.out.println("Teacher teach:"+i);
            }
            if (result != null && result.size() > 0){
                return true;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ListenableFuture<List<Integer>> asynExecutor(List<Teacher> teachers){

        List<ListenableFuture<Integer>> futures = new ArrayList<>();
        for (Teacher teacher : teachers){
            futures.add(executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return teacher.teach();
                }
            }));
        }
        return Futures.allAsList(futures);
    }
}
