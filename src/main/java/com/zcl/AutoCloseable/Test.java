package com.zcl.AutoCloseable;

/**
 * Created by zhaocl1 on 2018/7/6.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        try(
                Resource r1 = new Resource(1);
                Resource r2 = new Resource(2);
                ){
            r2.read();
            r1.read();

        }catch (Exception e){
            System.out.println("catch block :"+e.getLocalizedMessage());
        }finally {
            System.out.println("finally block");
        }
    }
}
