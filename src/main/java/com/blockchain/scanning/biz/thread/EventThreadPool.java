package com.blockchain.scanning.biz.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread pool for executing event consumers
 */
public class EventThreadPool {

    /**
     * Thread pool for executing event consumers
     */
    private static ThreadPoolExecutor threadPoolExecutor;

    /**
     * Initialize the thread pool
     * @param corePoolSize
     */
    public static void init(int corePoolSize) {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(corePoolSize * 10));
        }
    }

    /**
     * Add event consumer
     * @param runnable
     * @throws Exception
     */
    public static void submit(Runnable runnable) throws Exception {
        if(threadPoolExecutor == null){
            throw new Exception("To perform scanning tasks, you need to initialize the thread pool first, you can call the EventThreadPool.init(1) method to initialize");
        }
        threadPoolExecutor.submit(runnable);
    }
}
