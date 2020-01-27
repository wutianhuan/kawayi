package com.wutianhuan.blockingqueue;

import sun.font.FontRunIterator;

/**
 * 作者 wth
 * 日期 2020-01-27 16:12
 * 一个初始值为0的变量,两个线程对其交替操作,一个加1一个减1,来5轮
 */
class ShareDate {
    private volatile Integer number = 0;

    public synchronized void add() {
        while (number != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "\t invoke add() return number == " + number);
        notifyAll();
    }

    public synchronized void sub() {
        while (number == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "\t invoke sub() return number == " + number);
        notifyAll();
    }

}

public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareDate shareDate = new ShareDate();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareDate.add();
            }
        }, "addThread").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareDate.sub();
            }


        }, "subThread").start();
    }
}
