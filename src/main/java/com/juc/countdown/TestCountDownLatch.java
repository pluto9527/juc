package com.juc.countdown;

import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch：闭锁
 *      在完成某些运算时，只有其它所有线程的运算全部完成，当前运算才能继续执行
 *      拆分运算，主线程等待汇总结果。
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(latch);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("费时："+(end - start));
    }

}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                //是否异常都必须执行，防止异常，主线程执行不了
                latch.countDown();
            }
        }
    }
}