package com.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列
 */
public class TestBlockingQueue {

    public static void main(String[] args) {
        //BlockingQueue是阻塞队列根接口
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);

        //入队
        queue.offer("aaa");
        queue.offer("bbb");
        queue.offer("ccc");
        queue.offer("ddd");

        //出队
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

}
