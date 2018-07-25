package com.juc.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  1. ReadWriteLock : 读写锁
 *
 *  写写/读写 需要“互斥”
 *  读读 不需要互斥（可多线程同时访问）
 *
 */
public class TestReadWriteLock {
    public static void main(String[] args) {

    }
}

class ReadWriteDemo {
    private int num = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get() {
        lock.readLock().lock();//上锁
        try {
            System.out.println(Thread.currentThread().getName()+":"+num);
        } finally {
            lock.readLock().unlock();//释放锁
        }
    }

    //写
    public void set(int num) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        } finally {
            lock.writeLock().unlock();
        }
    }
}