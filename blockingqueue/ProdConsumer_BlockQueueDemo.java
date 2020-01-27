package com.wutianhuan.blockingqueue;

import com.sun.org.apache.regexp.internal.RE;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者 wth
 * 日期 2020-01-27 17:52
 * 消费者生产者阻塞队列实现case
 */
class MyResource {
    private boolean FLAG = true; //默认开启,进行生产以及消费
    private BlockingQueue<String> blockingQueue = null;
    private AtomicInteger atomicInteger = new AtomicInteger();//默认为0

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    //生产
    public void prod() throws InterruptedException {
        String data;
        boolean result;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            //生产者生产数据
            result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "\t生产数据" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t生产数据" + data + "失败");
            }
            //暂停一会线程
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace();}
        }
        System.out.println(Thread.currentThread().getName() + "\t生产结束");
    }

    //消费
    public void consumer() throws InterruptedException {
        String result;
        while (FLAG) {
            //消费者消费数据
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (result == null) {
                System.out.println(Thread.currentThread().getName() + "\t消费结束");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费数据" + result + "成功");
        }
    }

    //停止
    public void stop() {
        this.FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(3));
        new Thread(()->{
            try {
                System.out.println("开始生产数据");
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();
        new Thread(()->{
            try {
                System.out.println("开始消费数据");
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();
        //暂停一会线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("活动结束");
        myResource.stop();
    }
}
