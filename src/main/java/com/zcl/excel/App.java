package com.zcl.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaocl1 on 2018/6/10.
 */
public class App {

    public static Map<Integer,List<Person>> dayPerson(List<Person> persons){
        Map<Integer,List<Person>> map = new HashMap<>();
        for (Person p : persons) {
            List<Person> list;
            if (!map.containsKey(p.getDay())) {
                list = new ArrayList<>();
                list.add(p);
                map.put(p.getDay(), list);
            } else {
                list = map.get(p.getDay());
                list.add(p);
            }
        }
        return map;
    }

    public static Map<String,List<Person>> namePerson(List<Person> persons){
        Map<String,List<Person>> map = new HashMap<>();
        for (Person p : persons) {
            List<Person> list;
            if (!map.containsKey(p.getName())) {
                list = new ArrayList<>();
                list.add(p);
                map.put(p.getName(), list);
            } else {
                list = map.get(p.getName());
                list.add(p);
            }
        }
        return map;
    }




    public static void main(String[] args) {
        List<Person> persons = ExcelReadUtil.readExcel();
        Map<String,List<Person>> namePersons = namePerson(persons);
        ExcelCreateUtil.createExcel(namePersons);
        System.out.println("--over-");
    }

}
