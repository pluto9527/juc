package com.juc.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

//1. 自己写fork join框架
public class TestForkJoin {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 500000000L);

        Long sum = forkJoinPool.invoke(task);
        System.out.println(sum);
    }

//2. 普通for循环
    @Test
    public void test1() {
        Instant start = Instant.now();

        long sum = 0L;
        for (long i=0L; i<=500000000; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

//3. java8封装的forkjoin框架
    @Test
    public void test2() {
        Instant start = Instant.now();

        long sum = LongStream.rangeClosed(0L, 500000000L)
                .parallel()
                .reduce(0L, Long::sum);

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

}

class ForkJoinSumCalculate extends RecursiveTask<Long> {
    private long start;
    private long end;
    private static final long THURSHOLD = 10000L;//临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        //先判断是否已经小于临界值
        long length = end - start;
        if (length <= THURSHOLD) {
            long sum = 0;
            for (long i=start; i<=end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();//进行拆分，同时压入线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join()+right.join();
        }
    }
}
