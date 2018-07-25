package com.juc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *  非阻塞队列
 */
public class TestQueue {

    public static void main(String[] args) {
        //无边界队列，无长度限制
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        //入队
        queue.offer("aaa");
        queue.offer("bbb");
        queue.offer("ccc");

        //出队
        System.out.println(queue.poll());
        System.out.println(queue.size());

        System.out.println("----------");

        System.out.println(queue.peek());
        System.out.println(queue.size());
    }

}
