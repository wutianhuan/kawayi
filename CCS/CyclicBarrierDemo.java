package com.wutianhuan.CCS;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 作者 wth
 * 日期 2020-01-27 14:19
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(5);
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t到达会议室");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t开始发言");
            }, String.valueOf(i)).start();
        }
    }

    private static void callDragn() {
        CyclicBarrier cb = new CyclicBarrier(7, () -> System.out.println("召唤神龙"));
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                System.out.println("第" + Thread.currentThread().getName() + "颗龙珠已收集");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
