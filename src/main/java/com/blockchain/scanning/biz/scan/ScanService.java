package com.blockchain.scanning.biz.scan;

import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.biz.thread.EventConsumer;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.factory.ChainScannerFactory;
import com.blockchain.scanning.config.BlockChainConfig;

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
     * Configure the parameters required for this block scanning task
     */
    private BlockChainConfig blockChainConfig;
    /**
     * Queue, every time a block is scanned, a task will be placed in it, the scanned data will be processed asynchronously, and MonitorEvent will be called as needed
     */
    private EventQueue eventQueue;

    /**
     * Initialize all member variables
     * @param blockChainConfig
     */
    public void init(BlockChainConfig blockChainConfig) throws Exception {
        this.blockChainConfig = blockChainConfig;
        this.chainScanner = ChainScannerFactory.getChainScanner(blockChainConfig.getChainType());
        this.eventQueue = new EventQueue();

        chainScanner.init(blockChainConfig, eventQueue);

        EventThreadPool.submit(new EventConsumer(chainScanner, eventQueue));
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
