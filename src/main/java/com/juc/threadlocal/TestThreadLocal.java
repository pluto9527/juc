package com.juc.threadlocal;

/**
 * 结论：

 　　1. 对象一旦创建，无论属性是否改变都不会改变hashCode地址值

 　　2. ThreadLocal当前线程存入多个值会把前面的值覆盖掉

 */
public class TestThreadLocal {

    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        User user = new User();
        System.out.println(user);
        user.setName("zhang");
        System.out.println(user);
        user.setAge(10);
        System.out.println(user);

        tl.set("aaa");
        System.out.println(tl);
        tl.set("bbb");
        System.out.println(tl);
        tl.set("wsnd");
        System.out.println(tl);

        System.out.println(tl.get());
        System.out.println(tl.get());
        System.out.println(tl.get());
        System.out.println(tl.get());
    }
}
