package com.blockchain.scanning.biz.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread pool for executing event consumers
 */
public class EventThreadPool {

    /**
     * Thread pool for executing event consumers
     */
    private static ThreadPoolExecutor threadPoolExecutor;

    /**
     * Used to record the number of scanning jobs currently being executed
     */
    private static AtomicInteger numberOfActive;

    /**
     * Initialize the thread pool
     * @param corePoolSize Must >= number of tasks, recommended equal to number of tasks
     */
    public static void init(int corePoolSize) {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<>(corePoolSize * 10));
        }
        if(numberOfActive == null){
            numberOfActive = new AtomicInteger(0);
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

    /**
     * Record the number of tasks
     */
    public static void incrementTaskNumber(){
        numberOfActive.incrementAndGet();
    }

    /**
     * If there are less than 2 tasks remaining, the entire thread pool is closed.
     */
    public static synchronized void shutdownTask(){
        if(numberOfActive == null){
            shutdown();
            return;
        }

        int number = numberOfActive.get();

        if(number > 0){
            numberOfActive.decrementAndGet();
        }

        if(number <= 1){
            shutdown();
        }
    }

    /**
     * Stopping the thread pool
     */
    public static void shutdown(){
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }
}
