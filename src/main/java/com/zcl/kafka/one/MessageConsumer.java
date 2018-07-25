package com.zcl.kafka.one;

import com.zcl.order.OrderService;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by linjl3 on 2016/3/14.
 */
public class MessageConsumer implements Runnable {
    private KafkaStream<byte[], byte[]> m_stream;
    private int m_threadNumber;
    private OrderService orderService;
    private String topic;
    private static AtomicInteger counter = new AtomicInteger(0);

    public MessageConsumer(KafkaStream<byte[], byte[]> m_stream, int m_threadNumber, String topic, OrderService orderService) {
        this.m_stream = m_stream;
        this.m_threadNumber = m_threadNumber;
        this.topic = topic;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        int messageCount = 0;
        long strat = System.currentTimeMillis();
        System.out.println("start time:"+strat);
        while (it.hasNext()) {
            messageCount++;
            StringBuilder message = new StringBuilder();
            message.append("Thread ").append(m_threadNumber).append(" is consuming topic[").append(topic).append("] messageCount:").append(messageCount);
            String orderId = new String(it.next().message());
            try {
                System.out.println(message.append(" the message is: ").append(orderId));
                if (topic.equals("stock_seckill")) {
                    orderService.orderHandle(orderId);
                }

            } catch (Exception e) {
            }

            if (counter.incrementAndGet() >= 10000){
                long end = System.currentTimeMillis();
                System.out.println("Thread "+m_threadNumber+" consumer message end " + orderId +" cost "+( end - strat));
            }

        }

    }
}
