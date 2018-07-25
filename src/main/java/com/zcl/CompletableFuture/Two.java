package com.zcl.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhaocl1 on 2018/5/24.
 */
public class Two {
    public static void main(String[] args) {
        try {
            testGet();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void testGet() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            int i = 1/0;
            System.out.println("result="+0);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        int result = future.get();
        System.out.println("result="+result);
    }
}
