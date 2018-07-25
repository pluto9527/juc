package com.juc.cas;

/**
 *  模拟CAS算法：
 *  CAS(Compare-And-Swap) 算法保证数据的原子性
 *
 *      CAS算法是硬件(计算机系统) 对于并发操作共享数据的支持
 *      CAS算法 包含了三个操作数：
 *         内存值 V
 *         预估值 A
 *         更新值 B
 *         当且仅当 V==A 时，把B的值赋给V(V=B)，否则不做任何操作
 */
public class TestCAS {

    public static void main(String[] args) {
        CompareAndSwap cas = new CompareAndSwap();

        for (int i=0; i<10; i++) {
            new Thread(() -> {
                //先获取内存值作为期望的值
                int expectedValue = cas.get();
                //然后在比较修改值
                boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 100));
                System.out.println(b);
            }).start();
        }
    }
}

class CompareAndSwap {
    private volatile int value;

    //获取内存值V
    public synchronized int get() {
        return value;
    }

    //比较
    private synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        //如果别的线程已经修改了内存值，那么此时不会更新内存值
        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        //并把内存值返回
        return oldValue;
    }

    //设置(expectedValue是期望的老值，而oldValue可能被别的线程修改为新的值了)
    //注意：都是同步方法，只有一个线程访问，但操作系统底层用的不是synchronized
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }


}
