package com.zcl.future.futureDemo;

import java.util.concurrent.*;

/**
 * Created by zhaocl1 on 2018/7/14.
 */
public class Task {

    /**
     * 当一个线程需要等待另一个线程把某个任务执行完毕后它才能继续执行，此时可用FutrueTask
     * 如果有多个线程执行任务，每个任务只能被执行一次，当多个线程试图同时执行同一个任务时，只允许一个线程执行任务，其他线程需要等待这个任务
     * 执行完毕之后才能继续执行。可以用下面的代码
     */
    private final ConcurrentMap<Object,Future<String>> taskCache = new ConcurrentHashMap<>();
    public String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while (true){
            Future<String> future = taskCache.get(taskName);
            if(future == null){
                Callable<String> callable = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return taskName;
                    }
                };
                FutureTask<String> futureTask = new FutureTask<String>(callable);
                future = taskCache.putIfAbsent(taskName, futureTask);//首次put进去时返回null
                if(future == null){
                    future = futureTask;
                    futureTask.run();
                }
            }
            return future.get();
        }
    }

}
