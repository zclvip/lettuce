package com.zcl.future.futureDemo;

/**
 * Created by zhaocl1 on 2018/7/13.
 */
public class CommonCook {

    static class OnlineShop extends Thread{
        private Chuju chuju;
        @Override
        public void run() {
            System.out.println("step 1 : buy chuju");
            System.out.println("step 1 : delivery");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("step 1 : sin");
            chuju = new Chuju();
        }
    }
    static void cook(Chuju chuju,ShiCai shiCai){};
    static class Chuju{}
    static class ShiCai{}

    public static void main(String[] args) throws InterruptedException {
        long starttime = System.currentTimeMillis();
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.start();
        onlineShop.join();//阻塞主线程，等待该线程执行完毕在恢复主线程执行

        Thread.sleep(2000);
        ShiCai shiCai = new ShiCai();
        System.out.println("step 2 : shicai sign");
//        onlineShop.join();//join放在这里更合适，但是为了表现效果，放在上面了

        System.out.println("step 3 cooking");
        cook(onlineShop.chuju, shiCai);
        System.out.println("total cost time :"+(System.currentTimeMillis() - starttime));
    }

    /**
     * step 1 : buy chuju
     step 1 : delivery
     step 1 : sin
     step 2 : shicai sign
     step 3 cooking
     total cost time :7014
     *
     */
}
