package com.blockchain.scanning.chain.model;

import com.blockchain.scanning.chain.model.eth.EthTransactionModel;
import com.blockchain.scanning.chain.model.tron.TronTransactionModel;
import org.web3j.protocol.core.methods.response.EthBlock;

/**
 * Scanned transaction result
 */
public class TransactionModel {

    /**
     * Transaction objects on the Ethereum chain
     */
    private EthTransactionModel ethTransactionModel;

    /**
     * TransactionInfo on the Tron chain
     */
    private TronTransactionModel tronTransactionModel;

    // TODO SOL are under development, so there is no result set attribute for the time being

    public static TransactionModel builder(){
        return new TransactionModel();
    }

    public EthTransactionModel getEthTransactionModel() {
        return ethTransactionModel;
    }

    public TransactionModel setEthTransactionModel(EthTransactionModel ethTransactionModel) {
        this.ethTransactionModel = ethTransactionModel;
        return this;
    }

    public TronTransactionModel getTronTransactionModel() {
        return tronTransactionModel;
    }

    public TransactionModel setTronTransactionModel(TronTransactionModel tronTransactionModel) {
        this.tronTransactionModel = tronTransactionModel;
        return this;
    }
}
