package com.wutianhuan.CCS;

import org.junit.Test;
import sun.util.locale.provider.DateFormatProviderImpl;

import java.util.concurrent.CountDownLatch;

/**
 * 作者 wth
 * 日期 2020-01-27 13:24
 */
enum CountryEnum {

    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");
    private Integer retCode;
    private String retMessaeg;

    CountryEnum(Integer retCode, String retMessaeg) {
        this.retCode = retCode;
        this.retMessaeg = retMessaeg;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessaeg() {
        return retMessaeg;
    }
    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum element : countryEnums) {
            if(element.retCode==index){
                return element;
            }
        }
        return null;
    }
}

public class CountDownLatchDemo {
    public static void main(String[] args) {

        CountDownLatch cd = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "国被灭");
                cd.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessaeg()).start();
        }
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("秦国统一天下");
    }

    private static void closeDoor() {
        CountDownLatch cd = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t上完自习离开教室");
                cd.countDown();
            }, String.valueOf(i)).start();
        }
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 班长锁门");
    }
}
