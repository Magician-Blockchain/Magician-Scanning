package com.blockchain.scanning.chain;

import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.config.BlockChainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

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
     * start scanning
     */
    public void scanStart() {
        String logBeginBlockNumber = blockChainConfig.getBeginBlockNumber()
                .compareTo(BlockEnums.LAST_BLOCK_NUMBER.getValue()) == 0 ?
                BlockEnums.LAST_BLOCK_NUMBER.getText() :
                blockChainConfig.getBeginBlockNumber().toString();

        logger.info("start scanning, chainType: {}, beginBlockNumber: {}", blockChainConfig.getChainType().toString(), logBeginBlockNumber);

        try {
            BigInteger endBlockNumber = blockChainConfig.getBeginBlockNumber().add(BigInteger.valueOf(blockChainConfig.getScanSize()));

            scan(blockChainConfig.getBeginBlockNumber(), endBlockNumber);
        } catch (Exception e){
            logger.error("An exception occurred while scanning, chainType: {}, beginBlockNumber: {}", blockChainConfig.getChainType().toString(), logBeginBlockNumber, e);
        }
    }

    /**
     * Initialize all member variables
     * @param blockChainConfig
     * @param eventQueue
     */
    public void init(BlockChainConfig blockChainConfig, EventQueue eventQueue) {
        if(this.blockChainConfig == null){
            this.blockChainConfig = blockChainConfig;
        }

        if(this.eventQueue == null){
            this.eventQueue = eventQueue;
        }
    }

    /**
     * scan block
     * @param beginBlockNumber
     * @param endBlockNumber
     */
    public abstract void scan(BigInteger beginBlockNumber, BigInteger endBlockNumber);

    /**
     * Process the scanned transaction data and perform monitoring events on demand
     * @param transactionModel
     */
    public abstract void call(TransactionModel transactionModel);
}
