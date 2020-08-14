package com.imooc.mall.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-03
 */
public class ConditionDemo1 {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void method1() throws InterruptedException {
        lock.lock();
        try{
            System.out.println("条件不满足，开始await: "+Thread.currentThread().getName());
            condition.await();
            System.out.println("条件满足了，开始执行后续的任务"+Thread.currentThread().getName());
        }finally {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("unlock method1: "+simpleDateFormat.format(System.currentTimeMillis()));
            lock.unlock();
        }
    }

    void method2() {
        lock.lock();
        try{
            System.out.println("准备工作完成，唤醒其他的线程"+Thread.currentThread().getName());
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ConditionDemo1 conditionDemo1 = new ConditionDemo1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(simpleDateFormat.format(System.currentTimeMillis()));
                    conditionDemo1.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()));
        conditionDemo1.method1();
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()));
    }
}
