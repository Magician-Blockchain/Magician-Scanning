package com.blockchain.scanning.biz.scan;

import com.blockchain.scanning.biz.thread.*;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.RetryStrategy;
import com.blockchain.scanning.chain.factory.ChainScannerFactory;
import com.blockchain.scanning.commons.config.BlockChainConfig;

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
     * Queue, When a block height is skipped for some reason and the user has set a retry policy, the skipped block height will be placed in this queue and wait for a retry.
     */
    protected RetryStrategyQueue retryStrategyQueue;

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

        chainScanner.init(blockChainConfig, eventQueue, retryStrategyQueue);

        EventThreadPool.submit(new EventConsumer(chainScanner, eventQueue));
        if(this.retryStrategy != null){
            EventThreadPool.submit(new RetryStrategyConsumer(retryStrategyQueue, retryStrategy));
        }
    }

    /**
     * Start automatic scan
     * @throws Exception
     */
    public void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chainScanner.scanStart();
            }
        }, new Date(), blockChainConfig.getScanPeriod());
    }
}
