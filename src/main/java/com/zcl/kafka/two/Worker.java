package com.zcl.kafka.two;

import com.zcl.order.OrderService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaocl1 on 2018/4/28.
 */
public class Worker implements Runnable {

    private OrderService orderService;
    private String topic;
    private String message;
    private static AtomicInteger counter = new AtomicInteger(0);
    private static long strat = System.currentTimeMillis();



    public Worker(OrderService orderService, String topic,String message) {
        this.orderService = orderService;
        this.topic = topic;
        this.message = message;
    }

    public void run() {
        System.out.println("work Thread name="+Thread.currentThread().getName());
        if (topic.equals("stock_seckill")) {
            orderService.orderHandle(message);
        }

        if (counter.incrementAndGet() >= 10000){
            long end = System.currentTimeMillis();
            System.out.println("Thread name=" + Thread.currentThread().getName() + " worker end " + message + " cost " + (end - strat));
        }
    }
}
