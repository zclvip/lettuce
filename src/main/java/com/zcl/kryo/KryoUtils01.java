package com.zcl.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.minlog.Log;
import com.zcl.domain.OrderInfo;
import org.apache.log4j.Logger;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhaocl1 on 2018/5/4.
 */
public class KryoUtils01 {
    private static final Logger logger = Logger.getLogger(KryoUtils01.class);
    private static final String default_encoding = "UTF-8";

    private static KryoPool pool;
    static {
        try {
            KryoFactory factory = new KryoFactory() {
                @Override
                public Kryo create() {
                    Log.set(1);//开启kroy的trace的日志功能，可以看到具体的序列化和反序列化过程
                    Kryo kryo = new Kryo();
                    /**
                     * 不要轻易改变这里的设置，更改之后，序列化的格式就会发生变化，已经序列化的数据在反序列化就会报错
                     * 支持对象循环引用（否则会栈溢出）
                     * 默认就是true，这里就是提醒不要改变这个配置
                     */
                    kryo.setReferences(true);
                    /**
                     * 不强制要求注册类，默认就是false，不要改变这个配置
                     * 注册行为无法保证多个jvm内同一个类的注册编号相同，业务系统中大量的class也难以一一注册
                     */
                    kryo.setRegistrationRequired(false);
                    kryo.register(OrderInfo.class,78);
                    //Fix the NPE bug when deserializing Collections
                    ((Kryo.DefaultInstantiatorStrategy)kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
                    return kryo;
                }
            };
            pool = new KryoPool.Builder(factory).softReferences().build();
        } catch (Exception e) {
            logger.error("init KryoPool error,e=",e);
        }
    }

    /**
     * 将对象序列化为字节数组
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] writeToByteArray(final T obj){
        return pool.run(new KryoCallback<byte[]>() {
            @Override
            public byte[] execute(Kryo kryo) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Output output = new Output(byteArrayOutputStream);
                kryo.writeClassAndObject(output, obj);
                output.flush();
                return byteArrayOutputStream.toByteArray();
            }
        });
    }

    /**
     * 将字节数组反序列化为原对象
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> T readFromByteArray(final byte[] bytes){
        return pool.run(new KryoCallback<T>() {
            @Override
            public T execute(Kryo kryo) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                Input input = new Input(byteArrayInputStream);
                return (T)kryo.readClassAndObject(input);
            }
        });
    }


    /**
     * 只序列化对象，序列化的结果里，不包含类型的信息，码流更小
     * 将对象序列化为字节数组
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] writeObjectToByteArray(final T obj){
        return pool.run(new KryoCallback<byte[]>() {
            @Override
            public byte[] execute(Kryo kryo) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Output output = new Output(byteArrayOutputStream);
                kryo.writeObject(output,obj);
                output.flush();
                return byteArrayOutputStream.toByteArray();
            }
        });
    }

    /**
     * 将字节数组反序列化为原对象
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readObjectFromByteArray(final byte[] bytes,final Class<T> clazz){
        return pool.run(new KryoCallback<T>() {
            @Override
            public T execute(Kryo kryo) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                Input input = new Input(byteArrayInputStream);
                return (T)kryo.readObject(input,clazz);
            }
        });
    }

}
