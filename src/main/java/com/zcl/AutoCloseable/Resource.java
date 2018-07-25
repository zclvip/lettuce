package com.zcl.AutoCloseable;

/**
 * Created by zhaocl1 on 2018/7/6.
 */
public class Resource implements AutoCloseable {

    private int id;

    public Resource(int id) {
        this.id = id;
    }

    @Override
    public void close() throws Exception {
        System.out.println("-----Resouce closed-----"+id);
    }

    public void read() {
        System.out.println("read " + id);
        throw new RuntimeException("read error");
    }
}
