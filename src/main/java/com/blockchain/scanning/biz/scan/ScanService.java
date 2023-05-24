package com.blockchain.scanning.biz.scan;

import com.blockchain.scanning.biz.thread.*;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.RetryStrategy;
import com.blockchain.scanning.chain.factory.ChainScannerFactory;
import com.blockchain.scanning.commons.config.BlockChainConfig;

import java.math.BigInteger;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Business class for scanning blocks
 */
public class ScanService {

    /**
     * The main class used for scanning, there are currently 3 implementation classes, which can be extended in the future
     */
    private ChainScanner chainScanner;

    /**
     * Retry strategy, used to rescan skipped block heights
     */
    private RetryStrategy retryStrategy;

    /**
     * Configure the parameters required for this block scanning task
     */
    private BlockChainConfig blockChainConfig;
    /**
     * Queue, every time a block is scanned, a task will be placed in it, the scanned data will be processed asynchronously, and MonitorEvent will be called as needed
     */
    private EventQueue eventQueue;

    /**
     * Timed tasks to perform scanning tasks
     */
    private Timer timer;

    /**
     * Monitor events consumer
     */
    private EventConsumer eventConsumer;

    /**
     * Retry strategy consumer
     */
    private RetryStrategyConsumer retryStrategyConsumer;

    /**
     * Queue, When a block height is skipped for some reason and the user has set a retry policy, the skipped block height will be placed in this queue and wait for a retry.
     */
    protected RetryStrategyQueue retryStrategyQueue;

    /**
     * The maximum block height that has been scanned so far
     */
    private BigInteger currentBlockHeight;

    public Timer getTimer() {
        return timer;
    }

    public EventConsumer getEventConsumer() {
        return eventConsumer;
    }

    public RetryStrategyConsumer getRetryStrategyConsumer() {
        return retryStrategyConsumer;
    }

    public BigInteger getCurrentBlockHeight() {
        if(currentBlockHeight == null){
            return blockChainConfig.getBeginBlockNumber();
        }
        return currentBlockHeight;
    }

    public void setCurrentBlockHeight(BigInteger currentBlockHeight) {
        this.currentBlockHeight = currentBlockHeight;
    }

    /**
     * Initialize all member variables
     * @param blockChainConfig
     */
    public void init(BlockChainConfig blockChainConfig) throws Exception {
        this.blockChainConfig = blockChainConfig;
        this.chainScanner = ChainScannerFactory.getChainScanner(this.blockChainConfig.getChainType());
        this.retryStrategy = this.blockChainConfig.getRetryStrategy();
        this.eventQueue = new EventQueue();

        if(this.retryStrategy != null) {
            this.retryStrategyQueue = new RetryStrategyQueue();
        }

        chainScanner.init(blockChainConfig, eventQueue, retryStrategyQueue, this);

        eventConsumer = new EventConsumer(chainScanner, eventQueue);
        EventThreadPool.submit(eventConsumer);

        if(this.retryStrategy != null){
            retryStrategyConsumer = new RetryStrategyConsumer(retryStrategyQueue, retryStrategy);
            EventThreadPool.submit(retryStrategyConsumer);
        }

        EventThreadPool.incrementTaskNumber();
    }

    /**
     * Start automatic scan
     * @throws Exception
     */
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chainScanner.scanStart();
            }
        }, new Date(), blockChainConfig.getScanPeriod());
    }

    public void shutdown(){
        Timer timer = this.getTimer();
        if (timer != null) {
            timer.cancel();
        }

        EventConsumer eventConsumer = this.getEventConsumer();
        if (eventConsumer != null) {
            eventConsumer.setShutdown(true);
        }

        RetryStrategyConsumer retryStrategyConsumer = this.getRetryStrategyConsumer();
        if (retryStrategyConsumer != null) {
            retryStrategyConsumer.setShutdown(true);
        }

        EventThreadPool.shutdownTask();
    }
}
