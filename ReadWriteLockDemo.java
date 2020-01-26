package com.wutianhuan;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 作者 wth
 * 日期 2020-01-26 17:33
 */

/**
 * 读写锁
 */
class MyCache {
    //自定义一个缓存 volatile用来保证缓存的可见性
    private volatile Map<String, Object> map = new HashMap<>();
    //定义读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    //写操作
    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t开始写入: "+key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    //读操作
    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t开始读取: "+key);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t读取完成 :" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }

    }
}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //开启50个写线程
        for (int i = 0; i < 50; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }
        //开启50个读线程
        for (int i = 0; i < 50; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }

    }
}
