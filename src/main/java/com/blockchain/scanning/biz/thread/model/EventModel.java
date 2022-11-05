package com.blockchain.scanning.biz.thread.model;

import com.blockchain.scanning.chain.model.TransactionModel;

import java.math.BigInteger;
import java.util.List;

/**
 * Event entity
 * containing two fields, one is the block height of the transaction processed by this event and the other is the list of transaction objects processed by this event
 */
public class EventModel {

    /**
     * Current block height
     */
    private BigInteger currentBlockHeight;

    /**
     * List of Trading Objects
     */
    private List<TransactionModel> transactionModels;

    public static EventModel builder(){
        return new EventModel();
    }

    public BigInteger getCurrentBlockHeight() {
        return currentBlockHeight;
    }

    public EventModel setCurrentBlockHeight(BigInteger currentBlockHeight) {
        this.currentBlockHeight = currentBlockHeight;
        return this;
    }

    public List<TransactionModel> getTransactionModels() {
        return transactionModels;
    }

    public EventModel setTransactionModels(List<TransactionModel> transactionModels) {
        this.transactionModels = transactionModels;
        return this;
    }
}
