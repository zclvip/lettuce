package com.zcl.order.impl;

import com.zcl.order.OrderService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by zhaocl1 on 2018/4/26.
 */
@Service
public class OrderServiceImpl  implements OrderService{

    @Override
    public void orderHandle(String orderId) {
//        System.out.println("orderId:"+orderId);
        Random random = new Random();
        try {
            int cost = random.nextInt(200)+1;
            Thread.sleep(cost);
            System.out.println("orderId:"+orderId+" cost:"+cost+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            /**
             * nextInt(100) 获取的值区间是[0,99)
             */
            int rand = random.nextInt(200)+1;
            System.out.println("rand="+rand);
        }

    }
}
