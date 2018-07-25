package com.juc.copyonwrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  CopyOnWriteArrayList/CopyOnWriteArraySet : 写入并复制
 *      每次写入时都会复制一个新数组列表添加
 *
 *  注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 *
 */
public class TestCopyOnWriteList {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for (int i=0; i<10; i++) {
            new Thread(helloThread).start();
        }
    }

}

class HelloThread implements Runnable {
//    ConcurrentModificationException:并发修改异常
//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList list = new CopyOnWriteArrayList();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }
    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("DD");
        }
    }
}
