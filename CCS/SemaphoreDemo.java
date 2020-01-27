package com.wutianhuan.CCS;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 作者 wth
 * 日期 2020-01-27 14:36
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore sp = new Semaphore(3);
        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                try {
                    sp.acquire(1);//默认不写就是1
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    //暂停一会线程
                    try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace();}
                    System.out.println(Thread.currentThread().getName()+"\t驶出车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    sp.release(1);
                }
            },String.valueOf(i)).start();
        }
    }
}
