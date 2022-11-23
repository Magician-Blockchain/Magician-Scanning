package com.blockchain.scanning.chain.impl;

import com.blockchain.scanning.biz.thread.EventQueue;
import com.blockchain.scanning.biz.thread.model.EventModel;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.enums.BlockEnums;
import com.blockchain.scanning.commons.util.StringUtil;
import com.blockchain.scanning.config.BlockChainConfig;
import com.blockchain.scanning.config.EventConfig;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;
import com.blockchain.scanning.monitor.filter.InputDataFilter;
import com.blockchain.web3.MagicianWeb3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Scan Ethereum, all chains that support the Ethereum standard (BSC, POLYGAN, etc.)
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

        web3j = Web3j.build(blockChainConfig.getHttpService());
    }

    /**
     * scan block
     *
     * @param beginBlockNumber
     */
    @Override
    public void scan(BigInteger beginBlockNumber) {
        try {
            BigInteger lastBlockNumber = web3j.ethBlockNumber().send().getBlockNumber();

            if (beginBlockNumber.compareTo(BlockEnums.LAST_BLOCK_NUMBER.getValue()) == 0) {
                beginBlockNumber = lastBlockNumber;
            }

            if (beginBlockNumber.compareTo(lastBlockNumber) > 0) {
                logger.info("The block height on the chain has fallen behind the block scanning progress, pause scanning in progress ...... , scan progress [{}], latest block height on chain:[{}]", beginBlockNumber, lastBlockNumber);
                return;
            }

            EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(beginBlockNumber), true).send();
            if (block == null || block.getBlock() == null) {
                logger.info("Block height [{}] does not exist", beginBlockNumber);
                if(web3j.ethBlockNumber().send().getBlockNumber().compareTo(beginBlockNumber) > 0){
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));
                }
                return;
            }

            List<EthBlock.TransactionResult> transactionResultList = block.getBlock().getTransactions();
            if (transactionResultList == null || transactionResultList.size() < 1) {
                logger.info("No transactions were scanned on block height [{}]", beginBlockNumber);
                if(web3j.ethBlockNumber().send().getBlockNumber().compareTo(beginBlockNumber) > 0){
                    blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));
                }
                return;
            }

            List<TransactionModel> transactionList = new ArrayList<>();

            for (EthBlock.TransactionResult<EthBlock.TransactionObject> transactionResult : transactionResultList) {

                EthBlock.TransactionObject transactionObject = transactionResult.get();

                transactionList.add(TransactionModel.builder()
                        .setEthTransactionModel(transactionObject)
                );
            }

            eventQueue.add(EventModel.builder()
                    .setCurrentBlockHeight(beginBlockNumber)
                    .setTransactionModels(transactionList)
            );

            blockChainConfig.setBeginBlockNumber(beginBlockNumber.add(BigInteger.ONE));
        } catch (Exception e) {
            logger.error("An exception occurred while scanning, block height:[{}]", beginBlockNumber, e);
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
            if (transactionModel.getEthTransactionModel().getValue() == null) {
                transactionModel.getEthTransactionModel().setValue("0");
            }

            if (transactionObject.getInput() != null
                    && transactionObject.getInput().toLowerCase().startsWith("0x") == false) {
                transactionObject.setInput("0x" + transactionObject.getInput());
            }

            EthMonitorFilter ethMonitorFilter = ethMonitorEvent.ethMonitorFilter();

            if (ethMonitorFilter == null) {
                ethMonitorEvent.call(transactionModel);
                continue;
            }

            if (StringUtil.isNotEmpty(ethMonitorFilter.getFromAddress())
                    && (StringUtil.isEmpty(transactionObject.getFrom()) || ethMonitorFilter.getFromAddress().equals(transactionObject.getFrom().toLowerCase()) == false)) {
                continue;
            }

            if (StringUtil.isNotEmpty(ethMonitorFilter.getToAddress())
                    && (StringUtil.isEmpty(transactionObject.getTo()) || ethMonitorFilter.getToAddress().equals(transactionObject.getTo().toLowerCase()) == false)) {
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

            if (inputDataFilter(transactionObject, ethMonitorFilter) == false) {
                continue;
            }

            ethMonitorEvent.call(transactionModel);
        }
    }

    private boolean inputDataFilter(EthBlock.TransactionObject transactionObject, EthMonitorFilter ethMonitorFilter) {
        if (ethMonitorFilter.getInputDataFilter() == null) {
            return true;
        }

        if (StringUtil.isEmpty(transactionObject.getInput())
                || transactionObject.getInput().length() < 10) {
            return false;
        }

        InputDataFilter inputDataFilter = ethMonitorFilter.getInputDataFilter();

        if (StringUtil.isEmpty(inputDataFilter.getFunctionCode())) {
            return false;
        }

        if (transactionObject.getInput().toLowerCase().startsWith(inputDataFilter.getFunctionCode()) == false) {
            return false;
        }

        String inputData = "0x" + transactionObject.getInput().substring(10);

        if (inputDataFilter.getTypeReferences() != null
                && inputDataFilter.getTypeReferences().length >= 0
                && inputDataFilter.getValue() != null
                && inputDataFilter.getValue().length > 0) {

            if(inputDataFilter.getTypeReferences().length < inputDataFilter.getValue().length){
                return false;
            }

            List<Type> result = MagicianWeb3.getEthBuilder()
                    .getEthAbiCodec()
                    .decoderInputData(inputData, inputDataFilter.getTypeReferences());

            if (result == null || result.size() < inputDataFilter.getValue().length) {
                return false;
            }

            for (int i = 0; i < inputDataFilter.getValue().length; i++) {
                String value = inputDataFilter.getValue()[i];
                Type paramValue = result.get(i);

                if (paramValue == null || paramValue.getValue() == null) {
                    return false;
                }

                if(value == null){
                    continue;
                }

                if (value.equals(paramValue.getValue().toString().toLowerCase()) == false) {
                    return false;
                }
            }
        }

        return true;
    }
}
