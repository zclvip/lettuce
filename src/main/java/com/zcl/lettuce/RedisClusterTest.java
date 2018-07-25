package com.zcl.lettuce;

import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import com.lambdaworks.redis.cluster.api.sync.RedisAdvancedClusterCommands;
import com.lambdaworks.redis.codec.ByteArrayCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaocl1 on 2018/5/2.
 */
public class RedisClusterTest {

    public static void main(String[] args) {
        ArrayList<RedisURI> list = new ArrayList();
        list.add(RedisURI.create("redis://cache1.redis.dtc.uat:6408"));
        list.add(RedisURI.create("redis://cache2.redis.dtc.uat:6411"));
        list.add(RedisURI.create("redis://cache3.redis.dtc.uat:6404"));


        RedisURI redisURI = RedisURI.create("redis://cache1.redis.dtc.uat:6408");
        RedisClusterClient clusterClient = RedisClusterClient.create(redisURI);
        StatefulRedisClusterConnection<byte[],byte[]> connection = clusterClient.connect(ByteArrayCodec.INSTANCE);
        RedisAdvancedClusterCommands<byte[],byte[]> sync =  connection.sync();

//        String setResult = sync.set("zcl", "justTest");
//        System.out.println("setResult=" + setResult);
//
//        String getResult = sync.get("zcl");
//        System.out.println("getResult=" + getResult);
//
//        long delResult = sync.del("zcl");
//        System.out.println("delResult=" + delResult);

//        Map<String,String> map = new HashMap<String, String>();
//        map.put("2000","1000");
//        map.put("3000","1000");
//        map.put("4000", "10001");
//        sync.hmset("orderE", map);
//
//
//        List<String> result1 = sync.hmget("orderE","2000","3000");
//        List<String> result2 = sync.hmget("orderE","4000");
//
//        Map<String,String> map = new HashMap<String, String>();
//        map.put("2000","0");
//        map.put("3000", "0");
//        sync.hmset("orderE1000", map);
//
//        Map<String,String> map1 = new HashMap<String, String>();
//        map1.put("2000", "1");
//        sync.hmset("orderE1000", map1);
//
//        List<String> r1 = sync.hmget("orderE1000","2000");
//
//        Map<String,String> m1 = sync.hgetall("orderE1000");
        String receiveKeyPrefix = "RECEIVE_ORDER:";
        String orderListKey = "RECEIVE_ORDER:ORDERID";
        String lockPrefix = "PIPELINE_LOCK_ORDER:";
        Long orderId = 11749472L;
        sync.del((receiveKeyPrefix+orderId).getBytes());
        byte[] data = sync.get((receiveKeyPrefix + orderId).getBytes());
        Map<byte[],byte[]> map = sync.hgetall(orderListKey.getBytes());
        for (Map.Entry<byte[],byte[]> entry : map.entrySet()){
            System.out.println("key="+new String(entry.getKey())+" value="+new String(entry.getValue()));
        }
        sync.del(orderListKey.getBytes());
        byte[] lock = sync.get((lockPrefix+orderId).getBytes());

        System.out.println("---");
    }
}
