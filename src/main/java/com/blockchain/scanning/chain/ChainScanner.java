package com.blockchain.scanning.chain;

import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.biz.thread.RetryStrategyQueue;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.commons.config.BlockChainConfig;
import com.blockchain.scanning.commons.enums.ChainType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The main class used for scanning, there are currently 3 implementation classes, which can be extended in the future
 */
public abstract class ChainScanner {

    private Logger logger = LoggerFactory.getLogger(ScanService.class);

    /**
     * Configure the parameters required for this block scanning task
     */
    protected BlockChainConfig blockChainConfig;

    /**
     * Queue, every time a block is scanned, a task will be placed in it, the scanned data will be processed asynchronously, and MonitorEvent will be called as needed
     */
    protected EventQueue eventQueue;

    /**
     * Queue, When a block height is skipped for some reason and the user has set a retry policy, the skipped block height will be placed in this queue and wait for a retry.
     */
    protected RetryStrategyQueue retryStrategyQueue;

    /**
     * Used to implement polling load balancing
     */
    private AtomicInteger atomicInteger;

    /**
     * get chain type
     * @return
     */
    public ChainType getChainType(){
        return blockChainConfig.getChainType();
    }

    /**
     * start scanning
     */
    public void scanStart() {
        String logBeginBlockNumber = blockChainConfig.getBeginBlockNumber()
                .compareTo(BlockEnums.LAST_BLOCK_NUMBER.getValue()) == 0 ?
                BlockEnums.LAST_BLOCK_NUMBER.getText() :
                blockChainConfig.getBeginBlockNumber().toString();

        logger.info("start scanning, chainType: {}, beginBlockNumber: {}", blockChainConfig.getChainType().toString(), logBeginBlockNumber);

        try {
            scan(blockChainConfig.getBeginBlockNumber());
        } catch (Exception e){
            logger.error("An exception occurred while scanning, chainType: {}, beginBlockNumber: {}", blockChainConfig.getChainType().toString(), logBeginBlockNumber, e);
        }
    }

    /**
     * Initialize all member variables
     * @param blockChainConfig
     * @param eventQueue
     */
    public void init(BlockChainConfig blockChainConfig, EventQueue eventQueue, RetryStrategyQueue retryStrategyQueue) {
        if(this.blockChainConfig == null){
            this.blockChainConfig = blockChainConfig;
        }

        if(this.eventQueue == null){
            this.eventQueue = eventQueue;
        }

        if(this.retryStrategyQueue == null){
            this.retryStrategyQueue = retryStrategyQueue;
        }

        this.atomicInteger = new AtomicInteger(0);
    }

    /**
     * Add a block height that needs to be retried
     *
     * @param blockNumber
     */
    protected void addRetry(BigInteger blockNumber) {
        if (this.retryStrategyQueue != null) {
            this.retryStrategyQueue.add(blockNumber);
        }
    }

    /**
     * Get the node index by polling
     *
     * @return
     */
    protected int getNextIndex(int maxValue){
        int index = atomicInteger.get();

        if (index < (maxValue - 1)) {
            atomicInteger.incrementAndGet();
        } else {
            atomicInteger.set(0);
        }
        return index;
    }

    /**
     * scan block
     * @param beginBlockNumber
     */
    public abstract void scan(BigInteger beginBlockNumber);

    /**
     * Process the scanned transaction data and perform monitoring events on demand
     * @param transactionModel
     */
    public abstract void call(TransactionModel transactionModel);
}
