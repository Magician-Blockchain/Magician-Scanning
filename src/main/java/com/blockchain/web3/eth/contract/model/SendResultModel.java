package com.blockchain.web3.eth.contract.model;

import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

/**
 * After writing data to the contract, the feedback on the chain
 */
public class SendResultModel {

    /**
     * Feedback after initiating a transaction
     */
    private EthSendTransaction ethSendTransaction;

    /**
     * The transaction object returned after successfully sending the transaction to the chain
     */
    private EthGetTransactionReceipt ethGetTransactionReceipt;

    public EthSendTransaction getEthSendTransaction() {
        return ethSendTransaction;
    }

    public void setEthSendTransaction(EthSendTransaction ethSendTransaction) {
        this.ethSendTransaction = ethSendTransaction;
    }

    public EthGetTransactionReceipt getEthGetTransactionReceipt() {
        return ethGetTransactionReceipt;
    }

    public void setEthGetTransactionReceipt(EthGetTransactionReceipt ethGetTransactionReceipt) {
        this.ethGetTransactionReceipt = ethGetTransactionReceipt;
    }
}
