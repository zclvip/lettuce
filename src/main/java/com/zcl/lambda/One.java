package com.zcl.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhaocl1 on 2018/5/18.
 *
 * (params) -> expression
 * (params) -> statement
 * (params) -> { statements }
 */
public class One {

    public static void main(String[] args) {
        test3();
    }


    public static void test1(){
        String[] atp = {"apple","orange"};
        List<String> fl = Arrays.asList(atp);
        //使用lambda表达式
        fl.forEach((f) -> System.out.println(f + ";"));

        //java 8使用双冒号操作符
        fl.forEach(System.out::println);
    }

    /**
     * 用lambda 来实现runnable 接口
     */
    public static void test2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world! 匿名内部类");
            }
        }).start();

        new Thread(() -> System.out.println("hello world! lambda expression")).start();

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world ! 匿名内部类 runnable");
            }
        };
        run1.run();

        Runnable run2 = () -> System.out.println("hello world! lambda expression runnable");
        run2.run();
    }

    //使用lambda排序集合
    public static void test3(){
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};

//        Arrays.sort(players, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });

        //使用lambda expression
//        Comparator<String> sortByName = (String s1,String s2) -> (s1.compareTo(s2));
//        Arrays.sort(players,sortByName);

        //更简洁的方式
        Arrays.sort(players, (String s1, String s2) -> (s1.compareTo(s2)));

        for (int i=0;i<players.length;i++){
            System.out.println(players[i]);
        }
    }


}
