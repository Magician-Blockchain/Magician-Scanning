package com.blockchain.scanning.chain.impl;

import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.biz.thread.RetryStrategyQueue;
import com.blockchain.scanning.biz.thread.model.EventModel;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.config.BlockChainConfig;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.monitor.TronMonitorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.proto.Chain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * scan the tron chain
 *
 * TODO Filtered by condition, under development ......
 */
public class TronChainScanner extends ChainScanner {

    private Logger logger = LoggerFactory.getLogger(TronChainScanner.class);

    /**
     * TRON Node url
     */
    private List<ApiWrapper> apiWrappers;

    /**
     * Get a list of Tron listening events
     */
    private List<TronMonitorEvent> tronMonitorEvents;

    @Override
    public void init(BlockChainConfig blockChainConfig, EventQueue eventQueue, RetryStrategyQueue retryStrategyQueue) {
        super.init(blockChainConfig, eventQueue, retryStrategyQueue);

        this.apiWrappers = blockChainConfig.getApiWrappers();
        this.tronMonitorEvents = blockChainConfig.getEventConfig().getTronMonitorEvents();

    }

    @Override
    public void scan(BigInteger beginBlockNumber) {
        try {
            ApiWrapper apiWrapper = this.apiWrappers.get(getNextIndex(apiWrappers.size()));

            BigInteger lastBlockNumber = BigInteger.valueOf(apiWrapper.getNowBlock().getBlockHeader().getRawData().getNumber());

            if (beginBlockNumber.compareTo(BlockEnums.LAST_BLOCK_NUMBER.getValue()) == 0) {
                beginBlockNumber = lastBlockNumber;
            }

            if (beginBlockNumber.compareTo(lastBlockNumber) > 0) {
                logger.info("[TRON], The block height on the chain has fallen behind the block scanning progress, pause scanning in progress ...... , scan progress [{}], latest block height on chain:[{}]", beginBlockNumber, lastBlockNumber);
                return;
            }

            Chain.Block block = apiWrapper.getBlockByNum(beginBlockNumber.longValue());
            if (block == null) {
                logger.info("[TRON], Block height [{}] does not exist", beginBlockNumber);
                if (lastBlockNumber.compareTo(beginBlockNumber) > 0) {
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

                    //If the block is skipped, the retry strategy needs to be notified
                    addRetry(beginBlockNumber);
                }
                return;
            }

            List<Chain.Transaction> tronTransactionList = block.getTransactionsList();
            if (tronTransactionList == null || tronTransactionList.size() < 1) {
                logger.info("[TRON], No transactions were scanned on block height [{}]", beginBlockNumber);
                if (lastBlockNumber.compareTo(beginBlockNumber) > 0) {
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

                    //If the block is skipped, the retry strategy needs to be notified
                    addRetry(beginBlockNumber);
                }
                return;
            }


            List<TransactionModel> transactionList = new ArrayList<>();

            for(Chain.Transaction transaction : tronTransactionList){
                transactionList.add(TransactionModel.builder()
                        .setTronTransactionModel(transaction)
                );
            }

            eventQueue.add(EventModel.builder()
                    .setCurrentBlockHeight(beginBlockNumber)
                    .setTransactionModels(transactionList)
            );

            blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

        } catch (Exception e){
            logger.error("[TRON], An exception occurred while scanning, block height:[{}]", beginBlockNumber, e);
        }
    }

    @Override
    public void call(TransactionModel transactionModel) {
        // Chain.Transaction transaction = transactionModel.getTronTransactionModel();
        // TODO Filtered by condition, under development ......

        for(TronMonitorEvent tronMonitorEvent : this.tronMonitorEvents){
            tronMonitorEvent.call(transactionModel);
        }
    }
}
