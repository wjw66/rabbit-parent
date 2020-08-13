package com.wjw.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author : wjwjava01@163.com
 * @date : 5:04 2020/8/14
 * @description : 线程池
 */
public class ThreadPool {
    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 1024;

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();

    private static ExecutorService getThread =
            new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(QUEUE_SIZE), new ThreadPoolExecutor.AbortPolicy());

    public static void submit(Runnable runnable) {
        getThread.submit(runnable);
    }
}
