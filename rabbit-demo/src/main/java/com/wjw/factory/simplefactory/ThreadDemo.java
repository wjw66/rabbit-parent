package com.wjw.factory.simplefactory;

import com.wjw.thread.AppThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : wjwjava01@163.com
 * @date : 21:37 2020/8/14
 * @description :
 */
public class ThreadDemo {
    private static final AtomicInteger I = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = AppThreadPool.getPool();
        //1.线程1执行
        Future<String> future1 = executor.submit(ThreadDemo::demo);
        System.out.println(future1.get());
        //2.线程2执行
        Future<String> future2 = executor.submit(ThreadDemo::demo);
        System.out.println(future2.get());
        //3.线程3执行
        executor.execute(() -> {
            try {
                String s = future2.get() + "线程3接收线程2返回值执行";
                System.out.println(s);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        //4.线程4执行
        if (future2.get() != null) {
            executor.execute(() -> {
                try {
                    String s = future2.get() + "线程4接收线程2返回值执行";
                    System.out.println(s);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    public static String demo() throws InterruptedException {

        Thread.sleep(10000);
        I.incrementAndGet();
        System.out.println("i = " + I);
        return "Thread " + I + "执行结果 : ";
    }
}
