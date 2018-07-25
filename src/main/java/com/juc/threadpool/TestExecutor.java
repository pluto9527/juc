package com.juc.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *  创建线程方式四：线程池
 *
 *  一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度。
 *
 *  二、线程池的体系结构：
 *  java.util.concurrent.Executor ： 负责线程的使用与调度的根接口
 *      |--** ExecutorService 子接口 ：线程池的主要接口
 *          |-- ThreadPoolExecutor  线程池的实现类
 *          |-- ScheduledExecutorService 子接口 ： 负责线程的调度(可以定时执行任务)
 *              |-- ScheduledThreadPoolExecutor ： 继承ThreadPoolExecutor，实现ScheduledExecutorService
 *
 *  三、工具类：Executors (同Collections Arrays Files Collectors)
 *      创建线程池
 *      ExecutorService newFixedThreadPool() ： 创建固定大小的线程池
 *      ExecutorService newCachedThreadPool() ： 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量
 *      ExecutorService newSingleThreadExecutor() ：创建单个线程池。线程池中中有一个线程
 *
 *      创建线程调度
 *      ScheduledExecutorService newScheduledThreadPool() ：创建固定大小的线程，可以延迟或定时的执行任务
 *
 */
public class TestExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        //2. 为线程池中的线程分配任务
        List<Future<Integer>> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Future<Integer> future = pool.submit(() -> {
                int sum = 0;
                for (int n=0; n<=100; n++) {
                    sum += n;
                }
                return sum;
            });
            list.add(future);
        }

        /**
         * 3. 关闭线程池
         *      shutdown() ：会等待所有任务结束了之后，在关闭线程池
         *      shutdownNow() ： 不会等待任务结束，强制关闭线程池
         */
        pool.shutdown();

        //4. 打印结果
        for (Future<Integer> future : list) {
            System.out.println(future.get());
        }
    }

    public void task() {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        ThreadPoolDemo tp = new ThreadPoolDemo();
        //2. 为线程池中的线程分配任务
        for (int i=0; i<10; i++) {
            pool.submit(tp);
        }

        //3. 关闭线程池
        pool.shutdown();
    }

}

class ThreadPoolDemo implements Runnable {
    private int i = 0;
    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName()+":"+ ++i);
        }
    }
}
