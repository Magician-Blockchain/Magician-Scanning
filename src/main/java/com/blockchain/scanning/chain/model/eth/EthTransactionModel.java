package com.blockchain.scanning.chain.model.eth;

import org.web3j.protocol.core.methods.response.EthBlock;

/**
 * Transaction objects on the Ethereum chain
 */
public class EthTransactionModel {

    /**
     * Block Info
     */
    private EthBlock ethBlock;

    /**
     * Transaction objects
     */
    private EthBlock.TransactionObject transactionObject;

    public static EthTransactionModel builder(){
        return new EthTransactionModel();
    }

    public EthBlock getEthBlock() {
        ethBlock.getBlock().setTransactions(null);
        return ethBlock;
    }

    public EthTransactionModel setEthBlock(EthBlock ethBlock) {
        this.ethBlock = ethBlock;
        return this;
    }

    public EthBlock.TransactionObject getTransactionObject() {
        return transactionObject;
    }

    public EthTransactionModel setTransactionObject(EthBlock.TransactionObject transactionObject) {
        this.transactionObject = transactionObject;
        return this;
    }
}
