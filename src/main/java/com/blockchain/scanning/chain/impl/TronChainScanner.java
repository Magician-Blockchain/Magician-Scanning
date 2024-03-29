package com.blockchain.scanning.chain.impl;

import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.biz.thread.RetryStrategyQueue;
import com.blockchain.scanning.biz.thread.model.EventModel;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronBlockModel;
import com.blockchain.scanning.chain.model.tron.TronTransactionModel;
import com.blockchain.scanning.commons.config.BlockChainConfig;
import com.blockchain.scanning.commons.constant.TronConstants;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.commons.util.JSONUtil;
import com.blockchain.scanning.commons.util.StringUtil;
import com.blockchain.scanning.commons.util.okhttp.OkHttpUtil;
import com.blockchain.scanning.monitor.TronMonitorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<String> tronRpcUrls;

    /**
     * Get a list of Tron listening events
     */
    private List<TronMonitorEvent> tronMonitorEvents;

    @Override
    public void init(BlockChainConfig blockChainConfig, EventQueue eventQueue, RetryStrategyQueue retryStrategyQueue, ScanService scanService) {
        super.init(blockChainConfig, eventQueue, retryStrategyQueue, scanService);

        this.tronRpcUrls = blockChainConfig.getTronRpcUrls();
        this.tronMonitorEvents = blockChainConfig.getEventConfig().getTronMonitorEvents();

    }

    @Override
    public void scan(BigInteger beginBlockNumber) {
        try {
            String url = this.tronRpcUrls.get(getNextIndex(tronRpcUrls.size()));

            BigInteger lastBlockNumber = getBlock(url).getTronBlockHeaderModel().getTronRawDataModel().getNumber();

            if (beginBlockNumber.compareTo(BlockEnums.LAST_BLOCK_NUMBER.getValue()) == 0) {
                beginBlockNumber = lastBlockNumber;
            }

            if(scanService.getCurrentBlockHeight() == null){
                scanService.setCurrentBlockHeight(lastBlockNumber);
            }

            if (beginBlockNumber.compareTo(lastBlockNumber) > 0) {
                logger.info("[TRON], The block height on the chain has fallen behind the block scanning progress, pause scanning in progress ...... , scan progress [{}], latest block height on chain:[{}]", beginBlockNumber, lastBlockNumber);
                return;
            }

            if(blockChainConfig.getEndBlockNumber().compareTo(BigInteger.ZERO) > 0
                    && beginBlockNumber.compareTo(blockChainConfig.getEndBlockNumber()) >= 0){
                logger.info("[TRON], The current block height has reached the stop block height you set, so the scan job has been automatically stopped , scan progress [{}], end block height on chain:[{}]", beginBlockNumber, blockChainConfig.getEndBlockNumber());
                scanService.shutdown();
                return;
            }

            TronBlockModel tronBlockModel = getBlockByNum(url, beginBlockNumber);
            if (tronBlockModel == null) {
                logger.info("[TRON], Block height [{}] does not exist", beginBlockNumber);
                if (lastBlockNumber.compareTo(beginBlockNumber) > 0) {
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

                    //If the block is skipped, the retry strategy needs to be notified
                    addRetry(beginBlockNumber);
                }
                scanService.setCurrentBlockHeight(beginBlockNumber);
                return;
            }

            List<TronTransactionModel> tronTransactionList = tronBlockModel.getTransactions();
            if (tronTransactionList == null || tronTransactionList.size() < 1) {
                logger.info("[TRON], No transactions were scanned on block height [{}]", beginBlockNumber);
                if (lastBlockNumber.compareTo(beginBlockNumber) > 0) {
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

                    //If the block is skipped, the retry strategy needs to be notified
                    addRetry(beginBlockNumber);
                }
                scanService.setCurrentBlockHeight(beginBlockNumber);
                return;
            }


            List<TransactionModel> transactionList = new ArrayList<>();

            for(TronTransactionModel transaction : tronTransactionList){
                if(transaction == null){
                    continue;
                }
                transaction.setBlockID(tronBlockModel.getBlockID());
                transaction.setTronBlockHeaderModel(tronBlockModel.getTronBlockHeaderModel());
                transactionList.add(
                        TransactionModel.builder()
                                .setTronTransactionModel(transaction)
                );
            }

            eventQueue.add(EventModel.builder()
                    .setCurrentBlockHeight(beginBlockNumber)
                    .setTransactionModels(transactionList)
            );

            blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));

            scanService.setCurrentBlockHeight(beginBlockNumber);
        } catch (Exception e){
            logger.error("[TRON], An exception occurred while scanning, block height:[{}]", beginBlockNumber, e);
        }
    }

    @Override
    public void call(TransactionModel transactionModel) {
        // TODO Filtered by condition, under development ......

        for(TronMonitorEvent tronMonitorEvent : this.tronMonitorEvents){
            try {
                tronMonitorEvent.call(transactionModel);
            } catch (Exception e) {
                logger.error("[ETH], An exception occurred in the call method of the listener", e);
            }
        }
    }

    /**
     * Get the latest block
     * @param url
     * @return
     */
    private TronBlockModel getBlock(String url) throws Exception {
        String result = OkHttpUtil.postJson(url + TronConstants.GET_NOW_BLOCK, TronConstants.GET_NOW_BLOCK_PARAMETER);
        if(StringUtil.isEmpty(result)){
            throw new Exception("An exception occurred when obtaining the latest block, result: null");
        }
        return JSONUtil.toJavaObject(result, TronBlockModel.class);
    }

    /**
     * Obtain block information at high speed according to the block
     * @param url
     * @param blockNumber
     * @return
     */
    private TronBlockModel getBlockByNum(String url, BigInteger blockNumber) throws Exception {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("num", blockNumber);

        String result = OkHttpUtil.postJson(url + TronConstants.GET_BLOCK_BY_NUM, parameter);
        if(StringUtil.isEmpty(result)){
            throw new Exception("An exception occurred when querying blocks based on block height, result: null");
        }
        return JSONUtil.toJavaObject(result, TronBlockModel.class);
    }
}
