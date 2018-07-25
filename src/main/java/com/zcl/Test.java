package com.zcl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaocl1 on 2018/5/11.
 */
public class Test {

    public static void main(String[] args) {
        String code = "11300000";
        char s = code.charAt(2);
        System.out.println("charAt(2)="+s);

        System.out.println(code.charAt(3));
        System.out.println(code.charAt(3) != '0');

        String ss = "1000_23";
        System.out.println(ss.indexOf("j") == -1);
        System.out.println(ss);

        String orders = "{\"raiseThrowOrders\":[\"15115094\",\"15115095\"]}";

        JSONObject jsonObject = JSONObject.fromObject(orders);
        Set<String> keyset = jsonObject.keySet();
        System.out.println("keyset="+keyset);


        String orderStr = jsonObject.getString("raiseThrowOrders");
        System.out.println("orderStr="+orderStr);
        JSONArray array = jsonObject.getJSONArray("raiseThrowOrders");
        System.out.println("array=" + array);
        List<String> list = JSONArray.toList(array,String.class,new JsonConfig());
        List<String> list2 = (List<String>) JSONArray.toCollection(array);
        System.out.println("list="+list);
        System.out.println("list2="+list2);

    }


}
