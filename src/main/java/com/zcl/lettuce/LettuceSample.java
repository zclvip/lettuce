package com.zcl.lettuce;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisStringAsyncCommands;
import com.lambdaworks.redis.api.sync.RedisStringCommands;
import com.zcl.util.JacksonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhaocl1 on 2018/4/25.
 */
public class LettuceSample {

    static RedisClient client = RedisClient.create("redis://localhost");
    static StatefulRedisConnection<String,String> connection = client.connect();

    public static void main(String[] args) throws Exception {
//        basicUsage();

//        int result =1;
//        try {
//            result = 1/0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("-----ercepiton-------");
//        }
//        System.out.println("-----end-------");

        String result = JacksonUtil.toJson(null);
        System.out.println("------"+result);

        int i=128;
        String s = Integer.toBinaryString(i);
        System.out.println("s="+s);

        int i0 = i | (1 << 0);
        System.out.println(i0+" = "+Integer.toBinaryString(i0));

        int j = 1 << 6;
        System.out.println("j="+j+" "+Integer.toBinaryString(j));
        int i6 = i | (1 << 6);
        System.out.println(i6+" = "+Integer.toBinaryString(i6));
    }

    public static void basicUsage(){
        RedisStringCommands sync = connection.sync();
        String value = (String) sync.get("order_300");

        System.out.println("value=" + value);
    }

    public static void  async() throws ExecutionException, InterruptedException {
        RedisStringAsyncCommands<String,String> async = connection.async();
        RedisFuture<String> set = async.set("key", "value");
        RedisFuture<String> get = async.get("key");

        System.out.println(set.get());//OK
        System.out.println(get.get());//value
    }
}
