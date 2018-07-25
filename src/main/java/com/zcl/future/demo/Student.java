package com.zcl.future.demo;

import java.util.Random;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class Student implements Person{
    String name;

    public Student(String name) {
        this.name = name;
    }

    public int game(){
        int time = new Random().nextInt(1000);
        System.out.println(name+" game花费时间："+time);
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
