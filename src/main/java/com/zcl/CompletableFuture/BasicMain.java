package com.zcl.CompletableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhaocl1 on 2018/5/24.
 */
public class BasicMain {

    public static CompletableFuture<Integer> compute(){
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws IOException {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread{
            CompletableFuture<Integer> f;
            Client(String threadName,CompletableFuture<Integer> f){
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName()+" : "+f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        new Client("Client1",f).start();
        new Client("Client2",f).start();
        System.out.println("waiting");
        f.complete(100);
        System.in.read();
    }
}
