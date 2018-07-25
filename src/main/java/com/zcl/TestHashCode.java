package com.zcl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by zhaocl1 on 2018/7/25.
 */
public class TestHashCode {

    static class Key{
        private String k;

        public Key(String k) {
            this.k = k;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            return !(k != null ? !k.equals(key.k) : key.k != null);

        }

        @Override
        public int hashCode() {
            return k != null ? k.hashCode() : 0;
        }
    }

    static class Value{
        int v;

        public Value(int v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "v=" + v +
                    '}';
        }
    }

    public static void main(String[] args) {
        Map<Key,Value> map = new HashMap<>();
        Key k1 = new Key("A");
        Key k2 = new Key("A");
        map.put(k1,new Value(2));
        map.put(k2,new Value(3));
        System.out.println("k1.equals(k2):" + k1.equals(k2));
        System.out.println("map.get(k1);"+map.get(k1));
        System.out.println("map.get(k2):"+map.get(k2));

//        AbstractQueuedSynchronizer
    }
}
