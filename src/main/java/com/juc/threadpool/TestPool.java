package com.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestPool {
    public static void main(String[] args) {
        ExecutorService pool =
                new ThreadPoolExecutor(1, 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3));

        pool.execute(new TestThread());
        pool.execute(new TestThread());
        pool.execute(new TestThread());
        pool.execute(new TestThread());
        pool.execute(new TestThread());
        pool.execute(new TestThread());

        pool.shutdown();
    }
}

class TestThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}