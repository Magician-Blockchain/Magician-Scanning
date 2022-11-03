package com.blockchain.scanning.chain.impl;

import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.config.BlockChainConfig;
import com.blockchain.scanning.config.EventConfig;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Scan Ethereum, all chains that support the Ethereum standard (BSC, POLYGAN, XSC, etc.)
 */
public class ETHChainScanner extends ChainScanner {

    private Logger logger = LoggerFactory.getLogger(ETHChainScanner.class);

    /**
     * web3j
     */
    private Web3j web3j;

    /**
     * Get a list of Ethereum listening events
     */
    private List<EthMonitorEvent> ethMonitorEventList = EventConfig.getEthMonitorEvent();

    /**
     * Initialize all member variables
     *
     * @param blockChainConfig
     * @param eventQueue
     */
    @Override
    public void init(BlockChainConfig blockChainConfig, EventQueue eventQueue) {
        super.init(blockChainConfig, eventQueue);

        web3j = Web3j.build(new HttpService(blockChainConfig.getRpcUrl()));
    }

    /**
     * scan block
     *
     * @param beginBlockNumber
     * @param endBlockNumber
     */
    @Override
    public void scan(BigInteger beginBlockNumber, BigInteger endBlockNumber) {
        try {
            BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();

            if (beginBlockNumber.compareTo(blockNumber) > 0) {
                logger.error("The starting block height has exceeded the current maximum block height, please reset");
                return;
            }

            if (endBlockNumber.compareTo(blockNumber) > 0) {
                endBlockNumber = blockNumber;
            }

            for (BigInteger i = beginBlockNumber; i.compareTo(endBlockNumber) <= 0; i = i.add(BigInteger.ONE)) {
                blockChainConfig.setBeginBlockNumber(i);

                EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(i), true).send();
                if (block == null || block.getBlock() == null) {
                    continue;
                }

                List<TransactionModel> transactionList = new ArrayList<>();

                List<EthBlock.TransactionResult> transactionResultList = block.getBlock().getTransactions();
                if (transactionResultList == null || transactionResultList.size() < 1) {
                    continue;
                }

                for (EthBlock.TransactionResult<EthBlock.TransactionObject> transactionResult : transactionResultList) {

                    EthBlock.TransactionObject transactionObject = transactionResult.get();

                    transactionList.add(TransactionModel.builder()
                            .setEthTransactionModel(transactionObject)
                    );
                }

                eventQueue.add(transactionList);
            }
        } catch (Exception e) {
            logger.error("An exception occurred while scanning", e);
        }
    }

    /**
     * Process the scanned transaction data and perform monitoring events on demand
     *
     * @param transactionModel
     */
    @Override
    public void call(TransactionModel transactionModel) {
        EthBlock.TransactionObject transactionObject = transactionModel.getEthTransactionModel();

        for (EthMonitorEvent ethMonitorEvent : ethMonitorEventList) {
            EthMonitorFilter ethMonitorFilter = ethMonitorEvent.ethMonitorFilter();

            if(ethMonitorEvent != null) {
                if (ethMonitorFilter.getFromAddress() != null
                        && ethMonitorFilter.getFromAddress().equals("") == false
                        && ethMonitorFilter.getFromAddress().equals(transactionObject.getFrom().toLowerCase()) == false) {
                    continue;
                }

                if (ethMonitorFilter.getToAddress() != null
                        && ethMonitorFilter.getToAddress().equals("") == false
                        && ethMonitorFilter.getToAddress().equals(transactionObject.getTo().toLowerCase()) == false) {
                    continue;
                }

                if (ethMonitorFilter.getMinValue() != null
                        && ethMonitorFilter.getMinValue().compareTo(transactionModel.getEthTransactionModel().getValue()) > 0) {
                    continue;
                }

                if (ethMonitorFilter.getMaxValue() != null
                        && ethMonitorFilter.getMaxValue().compareTo(transactionModel.getEthTransactionModel().getValue()) < 0) {
                    continue;
                }

                if (ethMonitorFilter.getFunctionCode() != null && ethMonitorFilter.getFunctionCode().equals("") == false) {
                    if (transactionObject.getInput().length() < 10) {
                        continue;
                    }

                    if (transactionObject.getInput().toLowerCase().startsWith(ethMonitorFilter.getFunctionCode())) {
                        continue;
                    }
                }
            }

            ethMonitorEvent.call(transactionModel);
        }
    }
}
