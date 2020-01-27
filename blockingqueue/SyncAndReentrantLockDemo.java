package com.wutianhuan.blockingqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 作者 wth
 * 日期 2020-01-27 17:05
 * ReentrantLock精确唤醒case
 * 题目:多线程之间按顺序调用,实现A->B->C三个线程启动,要求如下:
 * AA打印5次,BB打印10次,CC打印15次
 * <p>
 * 来10轮
 */
class ShareResoure {
    private int number = 1;//A 1 B 2 C 3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() throws InterruptedException {
        lock.lock();
        try {
            //判断 避免虚假唤醒 使用while
            while (number != 1) {
                c1.await();
            }
            //执行
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //唤醒
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print10() throws InterruptedException {
        lock.lock();
        try {
            //判断 避免虚假唤醒 使用while
            while (number != 2) {
                c2.await();
            }
            //执行
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //唤醒
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15() throws InterruptedException {
        lock.lock();
        try {
            //判断 避免虚假唤醒 使用while
            while (number != 3) {
                c3.await();
            }
            //执行
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //唤醒
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResoure shareResoure = new ShareResoure();
        new Thread(() -> {

            try {
                for (int i = 0; i < 10; i++) {
                    shareResoure.print5();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "AAA").start();
        new Thread(() -> {

            try {
                for (int i = 0; i < 10; i++) {
                    shareResoure.print10();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "BBB").start();
        new Thread(() -> {

            try {
                for (int i = 0; i < 10; i++) {
                    shareResoure.print15();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "CCC").start();
    }
}
