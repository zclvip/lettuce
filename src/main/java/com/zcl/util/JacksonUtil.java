package com.zcl.util;

import com.lenovo.m2.arch.framework.domain.Money;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;


/**
 * Created by fenglg1 on 2015/5/19.
 */
public class JacksonUtil {

    public static final ObjectMapper mapper = new ObjectMapper();

    static{
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
         /*---money module---*/
        SimpleModule module = new SimpleModule("money", Version.unknownVersion());
        module.addSerializer(Money.class, new MoneySerializer());
        module.addDeserializer(Money.class, new MoneyDeserializer());

        mapper.registerModule(module);
    }
    public static String toJson(Object obj) {
        StringWriter writer = new StringWriter();
        JsonGenerator gen;
        try {
            gen = new JsonFactory().createJsonGenerator(writer);
            mapper.writeValue(gen, obj);
            gen.close();
            String json = writer.toString();
            writer.close();
            return json;
        } catch (IOException e) {
            System.out.println(e.getCause());
        }

        return null;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        Object object;
        try {
            object = mapper.readValue(json, classOfT);
            return (T) object;
        } catch (JsonParseException e) {
            System.out.println(e.getCause());
        } catch (JsonMappingException e) {
            System.out.println(e.getCause());
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
        return null;
    }



}

