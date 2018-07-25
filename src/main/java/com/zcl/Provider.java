package com.zcl;

import com.zcl.kafka.one.KafkaConsumer;
import com.zcl.kafka.two.KafkaConsumer2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaocl1 on 2018/4/26.
 */
public class Provider {

    private static ApplicationContext ctx;

    public static void main(String[] args) {
            ctx = new ClassPathXmlApplicationContext(
                    new String[]{
                            "applicationContext-resources.xml",
                            "kafka-consumer.xml"
                    }
            );
        System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
        long start = System.currentTimeMillis();
            ExecutorService executorService = Executors.newFixedThreadPool(1);
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    KafkaConsumer orderConsumer = (KafkaConsumer) ctx.getBean("orderConsumer");
//                    orderConsumer.run(30);
//
//                }
//            });

            executorService.submit(new Runnable() {
                public void run() {
                    KafkaConsumer2 orderConsumer2 = (KafkaConsumer2) ctx.getBean("orderConsumer");
                    orderConsumer2.run(1);
                }
            });
            executorService.shutdown();
        System.out.println("work end cost time :" + (System.currentTimeMillis() - start) + " ms");


    }
}
