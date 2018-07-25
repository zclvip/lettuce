package com.zcl.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhaocl1 on 2018/4/26.
 */
public class KryoUtil {

    private static final String default_encoding = "UTF-8";

    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            //支持对象循环引用，否则如果有循环引用会栈溢出，默认就是true
            kryo.setReferences(true);
            /**
             * 不强制要求注册类
             * 注册行为无法保存多个jvm内同一个类的注册编号相同，而且业务系统中的大量class也难以--注册  默认就是false
             */
            kryo.setRegistrationRequired(false);

            ////Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy)kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    /**
     * 获得当前线程的Kryo实例
     * @return
     */
    public static Kryo getInstance(){
        return kryoLocal.get();
    }

    /**
     * 将对象以及类型序列化为字节数组
     * @param obj 任意对象
     * @param <T> 对象类型
     * @return     序列化后的字节数组
     */
    public static <T> byte[] writeToByteArray(T obj){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        Kryo kryo = getInstance();
        kryo.writeClassAndObject(output,obj);
        output.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 将字节数组反序列化为原对象
     * @param bytes
     * @param <T>
     * @return
     */
    public static <T> T readFromByteArray(byte[] bytes){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        Kryo kryo = getInstance();
        return (T)kryo.readClassAndObject(input);
    }
}
