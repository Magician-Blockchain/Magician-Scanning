package com.blockchain.scanning.chain.model;

import org.web3j.protocol.core.methods.response.EthBlock;

/**
 * Scanned transaction result
 */
public class TransactionModel {

    /**
     * Transaction objects on the Ethereum chain
     */
    private EthBlock.TransactionObject ethTransactionModel;

    // TODO SOL, TRON are under development, so there is no result set attribute for the time being

    public static TransactionModel builder(){
        return new TransactionModel();
    }

    public EthBlock.TransactionObject getEthTransactionModel() {
        return ethTransactionModel;
    }

    public TransactionModel setEthTransactionModel(EthBlock.TransactionObject ethTransactionModel) {
        this.ethTransactionModel = ethTransactionModel;
        return this;
    }
}
