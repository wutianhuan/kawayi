package com.wutianhuan;

/**
 * 作者 wth
 * 日期 2020-01-28 16:48
 * 手写一个死锁
 */
class HoldLockThread implements Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t占用着"+lockA+"将要获取"+lockB);
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t成功获取"+lockB);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        new Thread(new HoldLockThread("lockA","lockB"),"AAAA").start();
        new Thread(new HoldLockThread("lockB","lockA"),"AAAA").start();
    }
}
