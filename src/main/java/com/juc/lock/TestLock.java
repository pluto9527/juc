package com.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  解决多线程安全问题的方式：
 *
 *  synchronized：隐式锁
 *
 *  1. 同步代码块
 *
 *  2. 同步方法
 *
 *  3. 同步锁Lock
 *      注意：Lock是一个显示锁，需要通过lock() 方法上锁，必须通过unlock() 方法进行释放锁
 *
 */
public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket implements Runnable {
    private int ticket = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();//上锁
            try {
                if (ticket > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"售出，余票："+ --ticket);
                }
            } finally {
                lock.unlock();//无论异常与否，都必须释放锁
            }
        }
    }
}