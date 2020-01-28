package com.wutianhuan.threadPool;

import java.util.concurrent.*;

/**
 * 作者 wth
 * 日期 2020-01-28 14:21
 * 手写一个线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,          //核心线程数
                5,      //最大线程数
                1L,         //空闲线程超时时间
                TimeUnit.SECONDS,       //超时时间单位
                new LinkedBlockingQueue<Runnable>(3), //阻塞队列
                Executors.defaultThreadFactory(),               //默认线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy());        //拒绝策略
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t执行业务");
                    //暂停一会线程
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
