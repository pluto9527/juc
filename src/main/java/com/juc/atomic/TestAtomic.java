package com.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  一、i++ 的原子性问题：
 *          i++ 的操作实际上分为三个步骤“读-改-写”
 *
 *      使用volatile不能解决原子性问题。
 *
 *  二、原子变量: jdk1.5后java.util.concurrent.atomic 提供了常用的原子变量
 *            1.volatile 保证内存可见性
 *            2.CAS(Compare-And-Swap) 算法保证数据的原子性
 *
 *              CAS算法是硬件(计算机系统) 对于并发操作共享数据的支持
 *              CAS算法 包含了三个操作数：
 *                  内存值 V
 *                  预估值 A
 *                  更新值 B
 *                  当且仅当 V==A 时，把B的值赋给V(V=B)，否则不做任何操作
 *
 */
public class TestAtomic {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i=0; i<10; i++) {
            new Thread(ad).start();
        }
    }

}

class AtomicDemo implements Runnable {
    //使用volatile不能解决原子性问题
    //private volatile int num = 0;

    private AtomicInteger num = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getNum());
    }
    private int getNum() {
//        return num++;
        return num.getAndIncrement();
    }
}