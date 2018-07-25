package com.zcl.future.demo;

import java.util.Random;

/**
 * Created by zhaocl1 on 2018/7/12.
 */
public class Teacher implements Person{
    String name;

    public Teacher(String name) {
        this.name = name;
    }

    public int teach(){
        int time = new Random().nextInt(200);
        System.out.println(name+" 讲课花费时间："+time);
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
