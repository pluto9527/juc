package com.juc.threadpool;

import java.util.Random;
import java.util.concurrent.*;

/**
 *  创建线程调度
 *      ScheduledExecutorService newScheduledThreadPool() ：创建固定大小的线程，可以延迟或定时的执行任务
 */
public class TestSchedule {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i=0; i<5; i++) {
            Future<Integer> future = pool.schedule(() -> {
                //延迟执行
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName()+":"+num);
                return num;
            }, 3, TimeUnit.SECONDS);

//            System.out.println(future.get());
        }

        pool.shutdown();
    }
}
