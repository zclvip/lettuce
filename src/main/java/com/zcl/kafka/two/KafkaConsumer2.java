package com.zcl.kafka.two;

import com.zcl.order.OrderService;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by linjl3 on 2016/3/14.
 */
public class KafkaConsumer2 {
    private ConsumerConnector consumer;
    private String topic;
    private ExecutorService executor;
    private int partitionCount;
    @Autowired
    private OrderService orderService;

    public int getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(int partitionCount) {
        this.partitionCount = partitionCount;
    }

    public KafkaConsumer2() {
    }

    public KafkaConsumer2(String a_zookeeper, String a_groupId, String a_topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;
    }


    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("KafkaConsumer shutdown: Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("KafkaConsumer shutdown: Interrupted during shutdown, exiting uncleanly");
        }
    }

    /**
     * 订阅主题
     * @param topic
     */
    public void subscribe(String topic) {
        this.topic = topic;
    }

    /**
     * 多线程消费，建议和partition数量一致
     */
    public void run(int partitionNum) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(partitionNum));// 描述读取哪个topic，需要几个线程读
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);// 创建Streams
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);// 每个线程对应于一个KafkaStream
        // now launch all the threads
        executor = Executors.newFixedThreadPool(streams.size());
        // now create an object to consume the messages
        int threadNumber = 0;
        try {
            for (final KafkaStream stream : streams) {
                executor.submit(new MessageConsumer2(stream, threadNumber, topic, orderService,"consumerThread0"));
                threadNumber++;
            }
        } catch (Exception e) {
            System.out.println("Run message consumer catch Exception. " + e);
        }
    }

    private ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.sync.time.ms","2000");
        props.put("zookeeper.session.timeout.ms","5000");
        props.put("zookeeper.connection.timeout.ms","6000");
        props.put("auto.commit.interval.ms","1000");
        props.put("auto.offset.reset","smallest");
        props.put("rebalance.max.retries","50");
        props.put("rebalance.backoff.ms","1200");

        return new ConsumerConfig(props);
    }

}