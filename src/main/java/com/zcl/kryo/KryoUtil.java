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
            //֧�ֶ���ѭ�����ã����������ѭ�����û�ջ�����Ĭ�Ͼ���true
            kryo.setReferences(true);
            /**
             * ��ǿ��Ҫ��ע����
             * ע����Ϊ�޷�������jvm��ͬһ�����ע������ͬ������ҵ��ϵͳ�еĴ���classҲ����--ע��  Ĭ�Ͼ���false
             */
            kryo.setRegistrationRequired(false);

            ////Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy)kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    /**
     * ��õ�ǰ�̵߳�Kryoʵ��
     * @return
     */
    public static Kryo getInstance(){
        return kryoLocal.get();
    }

    /**
     * �������Լ��������л�Ϊ�ֽ�����
     * @param obj �������
     * @param <T> ��������
     * @return     ���л�����ֽ�����
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
     * ���ֽ����鷴���л�Ϊԭ����
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
