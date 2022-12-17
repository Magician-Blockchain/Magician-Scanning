package com.blockchain.scanning.biz.thread;

import com.blockchain.scanning.chain.RetryStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Retry strategy consumer
 */
public class RetryStrategyConsumer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(RetryStrategyConsumer.class);

    /**
     * Queue, When a block height is skipped for some reason and the user has set a retry policy, the skipped block height will be placed in this queue and wait for a retry.
     */
    private RetryStrategyQueue retryStrategyQueue;

    /**
     * Retry strategy, used to rescan skipped block heights
     */
    private RetryStrategy retryStrategy;

    /**
     * Whether to stop
     */
    private boolean shutdown;

    public RetryStrategyConsumer(RetryStrategyQueue retryStrategyQueue, RetryStrategy retryStrategy){
        this.retryStrategyQueue = retryStrategyQueue;
        this.retryStrategy = retryStrategy;
        this.shutdown = false;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public void run() {
        while(true) {
            BigInteger blockNumber = null;
            try {
                blockNumber = this.retryStrategyQueue.getLinkedBlockingQueue().poll(2000, TimeUnit.MILLISECONDS);
                if(blockNumber == null){
                    if(shutdown){
                        return;
                    }
                    continue;
                }

                logger.info("Start rescanning block height:[{}]", blockNumber);

                retryStrategy.retry(blockNumber);
            }catch (Exception e){
                if(blockNumber != null){
                    logger.error("Rescan block with exception, block height:[{}]", blockNumber, e);
                } else {
                    logger.error("Rescan block with exception", e);
                }
            }
        }
    }
}
