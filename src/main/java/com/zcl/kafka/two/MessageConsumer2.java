package com.zcl.kafka.two;

import com.zcl.order.OrderService;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by linjl3 on 2016/3/14.
 */
public class MessageConsumer2 implements Runnable {
    private KafkaStream<byte[], byte[]> m_stream;
    private int m_threadNumber;
    private OrderService orderService;
    private String topic;
    private String threadName;
    private static AtomicInteger counter = new AtomicInteger(0);
    private static long strat = System.currentTimeMillis();
    private static ExecutorService executor;
//    private static ExecutorService executor = new ThreadPoolExecutor(30,30,0L,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());

    public MessageConsumer2(KafkaStream<byte[], byte[]> m_stream, int m_threadNumber, String topic, OrderService orderService,String threadName) {
        this.m_stream = m_stream;
        this.m_threadNumber = m_threadNumber;
        this.topic = topic;
        this.orderService = orderService;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        int messageCount = 0;
        //��30�������߳�
        executor = new ThreadPoolExecutor(30,30,0L,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());
        System.out.println("------------consumer2 run---");
        while (it.hasNext()) {
            messageCount++;
            System.out.println("------------messageCount=="+messageCount);

            StringBuilder message = new StringBuilder();
            message.append("Thread ").append(m_threadNumber).append(" is consuming topic[").append(topic).append("] messageCount:").append(messageCount);
            String orderId = new String(it.next().message());

            //ȡ����Ϣ��ͼ�����ӡ
            if (counter.incrementAndGet() >= 10000){
                long end = System.currentTimeMillis();
                System.out.println("Thread "+m_threadNumber+" consumer message end " + orderId +" cost "+( end - strat));
            }

            try {
                System.out.println(message.append(" the message is: ").append(orderId));
                executor.submit(new Worker(orderService, topic, orderId));

            } catch (Exception e) {
            }
        }

    }
}
